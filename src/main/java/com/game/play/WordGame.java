package com.game.play;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import com.game.platform.Stratergy;


public class WordGame implements Runnable {

	enum GameMode {
		USER,COMPUTER;
	}
	
	enum Synchornizer {
		SEMAPHORES,CONDITIONOBJECTS,LATCHES,LOCKS;
	}
	
	public WordGame(){
		
	}

	public void run() {
		Stratergy.instance().init();
		Stratergy.instance().printOut("Welcome to WordGame!! (READ RULES @...)");
		PlayerThread[] playerThreads = new PlayerThread[2];
		createPlayerThreads(playerThreads, Synchornizer.SEMAPHORES);
		for (PlayerThread playerThread : playerThreads) {
			playerThread.start();
		}
		Stratergy.instance().awaitDone();
		Stratergy.instance().printOut(Thread.currentThread().getName()+",End");
	}
	
	/**
	 * 
	 * @param playerThreads
	 * @param synchronizer
	 */
	private void createPlayerThreads(Constants[] playerThreads,Synchornizer synchronizer) {
		Semaphore userSema = new Semaphore(1); // USER starts unlocked.
        Semaphore computerSema = new Semaphore(0);
        final Stack<String> sharedStack = new Stack<>();
        AtomicBoolean gameNotCompleted = new AtomicBoolean(true);
        playerThreads[0] = new PlayerThreadSemaphore(GameMode.USER,userSema,computerSema,sharedStack,gameNotCompleted);
        playerThreads[1] = new PlayerThreadSemaphore(GameMode.COMPUTER,computerSema,userSema,sharedStack,gameNotCompleted);
	}

}
