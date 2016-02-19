package com.game.play;

import java.util.Random;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import com.game.platform.Stratergy;
import com.game.play.WordGame.GameMode;

abstract class PlayerThread extends Thread implements Constants {

	protected volatile Stack<String> sharedStack;
	protected final GameMode threadNameString;
	protected AtomicBoolean gameNotCompleted;
	protected GamePhase gameStage = GamePhase.INIT;
	protected volatile StringBuffer wordMem = new StringBuffer();
	protected volatile String lastResponse = "";

	PlayerThread(Stack<String> sharedStack, GameMode threadNameString,
			AtomicBoolean gameNotCompleted) {
		this.sharedStack = sharedStack;
		this.threadNameString = threadNameString;
		this.gameNotCompleted = gameNotCompleted;
	}

	protected abstract void acquire();

	protected abstract void release();

	public void run() {
		while (true && gameNotCompleted.get()) {
			acquire();
			if (gameNotCompleted.get())
				process();
			release();
		}
		Stratergy.instance().done();// call, done after all processing
	}

	private void process() {
		switch (threadNameString) {
		case USER:
			if (sharedStack != null) {
				Stratergy.instance().printOut(threadNameString + ",word/letter:");
				String input = Stratergy.instance().read();
				int blufCounter = 0;
				while (input == null || "".equals(input) || (gameStage == GamePhase.INIT && input.equalsIgnoreCase(MAGIC_WORD)) ) {
					if (blufCounter == 2) {
						Stratergy.instance().printOut("ENDING Game,rule violation for " + blufCounter+ ", times");
						stopGame(true, false);
						break;
					}
					String outValue = (gameStage == GamePhase.INIT) ? ",Enter a valid word/letter:": ",Enter a valid word/letter OR enter 'Challenge':";
					Stratergy.instance().printOut(threadNameString + outValue);
					input = Stratergy.instance().read();
					blufCounter++;
				}
				sharedStack.add(input);
				this.gameStage = GamePhase.START;
			}
			break;
		case COMPUTER:
			if (sharedStack != null) {
				if (!sharedStack.isEmpty()) {
					String lastValue = sharedStack.pop();
					if (lastValue.equalsIgnoreCase(Constants.MAGIC_WORD)
							|| lastResponse.equalsIgnoreCase(MAGIC_WORD)) {// end game
						if (!lastResponse.equalsIgnoreCase(MAGIC_WORD) && lookup(wordMem.toString())) {
							Stratergy.instance().printOut("COMPUTER,Win-I found word!"+ Constants.getDictionary().get(wordMem.toString()));
						} else if (lastResponse.equalsIgnoreCase(MAGIC_WORD)) {
							if (lastValue.startsWith(wordMem.toString())) {
								Stratergy.instance().printOut("USER,Win");
							} else {
								Stratergy.instance().printOut("COMPUTER,Win, the word should start with:"+ wordMem.toString());
							}
						} else {
							Stratergy.instance().printOut("USER,Win-I don't know word,with="+ wordMem.toString());
						}
						stopGame(true, false);
						break;
					} else {
						wordMem.append(lastValue);
						lastResponse = computerResponse();
						Stratergy.instance().printOut(threadNameString + ", word/letter:"+ lastResponse);
						if (!lastResponse.equalsIgnoreCase(MAGIC_WORD))
							wordMem.append(lastResponse);
					}
				}
			}
			break;
		default:
			Stratergy.instance().printOut("WRONG SETUP");
			break;
		}

	}

	private String computerResponse() {
		Random r = new Random();
		String response = r.nextBoolean() ? Constants.MAGIC_WORD : new String().valueOf(randomChar());
		return response;
	}

	private boolean lookup(String string) {
		return (Constants.getDictionary().get(string) != null);
	}

	/**
	 * For testing
	 */
	void stopGame() {
		if (new Random().nextBoolean()) {
			Stratergy.instance().printOut(threadNameString + ",Stopping game?"+ gameNotCompleted.compareAndSet(true, false));
		}
	}

	void stopGame(boolean expected, boolean current) {
		gameNotCompleted.compareAndSet(expected, current);
	}

	char randomChar() {
		Random r = new Random();
		return (alphabet.charAt(r.nextInt(alphabet.length())));
	}

}