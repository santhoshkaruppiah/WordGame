package com.game.play;

import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import com.game.play.WordGame.GameMode;

public class PlayerThreadSemaphore extends PlayerThread {
	
	private final Semaphore mine;
	private final Semaphore other;

	public PlayerThreadSemaphore(GameMode name, Semaphore mine,Semaphore other,Stack<String> sharedStack,AtomicBoolean gameNotCompleted) {
		super(sharedStack,name,gameNotCompleted);
		this.mine = mine;
		this.other = other;
	}

	@Override
	protected void acquire() {
        mine.acquireUninterruptibly();
	}

	@Override
	protected void release() {
		other.release();
	}

}
