package com.demo;

import com.game.platform.PlatformFactory;
import com.game.platform.Stratergy;
import com.game.play.Constants;
import com.game.play.WordGame;


/**
 * Main class to start playing word game
 */
public class App 
{
    public static void main( String[] args )
    {
    	Constants.load();
    	Thread.currentThread().setName("Main-Thread");
    	Stratergy.instance(new PlatformFactory(System.out).makePlatformStrategy());
    	WordGame wordGame = new WordGame();
    	Thread gameThread = new Thread(wordGame,"WordGame");
    	gameThread.start();
    }
}
