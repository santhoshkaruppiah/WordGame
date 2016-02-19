package com.game.platform;

public abstract class Stratergy {

	public static final int NUMBER_OF_THREADS = 2;

    private static Stratergy instance = null;
    
    protected Stratergy() {}

    public static Stratergy instance() {
        return instance;
    }

    public static Stratergy instance(Stratergy platform) {
        return instance = platform;
    }

    public abstract void init();

    public abstract String read();
    
    public abstract void printOut(String outputString);

    public abstract void done();

    public abstract void awaitDone();
}
