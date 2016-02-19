package com.game.platform;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class WordGameConsoleStratergy extends Stratergy {

	private final PrintStream out;
	private final Scanner scanner = new Scanner(System.in);
	
	private CountDownLatch exitBarrier = null;

	public WordGameConsoleStratergy(Object out) {
		this.out = (PrintStream) out;
	}

	@Override
	public void init() {
		exitBarrier = new CountDownLatch(NUMBER_OF_THREADS);
	}

	@Override
	public void printOut(String outputString) {
		out.println(outputString);
	}

	@Override
	public void done() {
		exitBarrier.countDown();
	}

	@Override
	public void awaitDone() {
		try {
			exitBarrier.await();
		} catch (java.lang.InterruptedException e) {

		}
	}

	@Override
	public String read() {
		return (scanner.nextLine());
	}

}
