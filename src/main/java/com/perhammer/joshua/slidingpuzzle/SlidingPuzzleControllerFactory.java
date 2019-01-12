package com.perhammer.joshua.slidingpuzzle;

import java.util.Random;

public class SlidingPuzzleControllerFactory {

    private Random random;

    private SlidingPuzzleControllerFactory(Random random) {
        this.random = random;
    }

    public static SlidingPuzzleControllerFactory getFactory() {
        return getFactory(new Random());
    }

    public static SlidingPuzzleControllerFactory getFactory(Random random) {
        return new SlidingPuzzleControllerFactory(random);
    }

    public SlidingPuzzleController get3by3game() {
        return getSquareGame(3);
    }
    public SlidingPuzzleController get4by4game() {
        return getSquareGame(4);
    }
    public SlidingPuzzleController get5by5game() {
        return getSquareGame(5);
    }

    private SlidingPuzzleController getSquareGame(int i) {
        return new SlidingPuzzleController( i, i, random);
    }

}
