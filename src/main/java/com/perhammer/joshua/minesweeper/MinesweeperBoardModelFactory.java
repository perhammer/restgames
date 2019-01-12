package com.perhammer.joshua.minesweeper;

import java.util.Random;

public class MinesweeperBoardModelFactory {
    private static final int BEGINNER_HEIGHT = 8;
    private static final int BEGINNER_WIDTH = 8;
    private static final int BEGINNER_MINES = 10;

    private static final int INTERMEDIATE_HEIGHT = 16;
    private static final int INTERMEDIATE_WIDTH = 16;
    private static final int INTERMEDIATE_MINES = 40;

    private static final int EXPERT_HEIGHT = 16;
    private static final int EXPERT_WIDTH = 31;
    private static final int EXPERT_MINES = 99;

    private static final int INSANE_HEIGHT = 61;
    private static final int INSANE_WIDTH = 64;
    private static final int INSANE_MINES = 999;

    private static final int SNAKE_HEIGHT = 173;
    private static final int SNAKE_WIDTH = 173;
    private static final int SNAKE_MINES = 9999;

    /*
    Beginner: 10 mines, 8x8 = 64; 15.625% mines
    Intermediate: 40 mines, 16x16 = 256; 15.625% mines
    Expert: 99 mines, 16x30 = 480; 20.625%
    Insane: 999 mines, 61x64 = 3904; 25.589%
    Snake: 9999 mines, 173x173 = 29929; 33.409 % mines
     */

    private Random random;

    public MinesweeperBoardModelFactory() {
        this.random = new Random();
    }

    public MinesweeperBoardModelFactory(Random random) {
        this.random = random;
    }

    public MinesweeperBoardModel getBeginnerBoard() {
        return getCustomBoard(BEGINNER_WIDTH, BEGINNER_HEIGHT,  BEGINNER_MINES);
    }

    public MinesweeperBoardModel getIntermediateBoard() {
        return getCustomBoard(INTERMEDIATE_WIDTH, INTERMEDIATE_HEIGHT, INTERMEDIATE_MINES);
    }

    public MinesweeperBoardModel getExpertBoard() {
        return getCustomBoard(EXPERT_WIDTH, EXPERT_HEIGHT, EXPERT_MINES);
    }

    public MinesweeperBoardModel getInsaneBoard() {
        return getCustomBoard(INSANE_WIDTH, INSANE_HEIGHT, INSANE_MINES);
    }

    public MinesweeperBoardModel getSnakeBoard() {
        return getCustomBoard(SNAKE_WIDTH, SNAKE_HEIGHT, SNAKE_MINES);
    }

    public MinesweeperBoardModel getCustomBoard(int width, int height, int numberOfMines) {
        if (numberOfMines> (width*height)) {
            throw new IllegalArgumentException("Unable to fit "+numberOfMines+" mine(s) onto a board of only "+(width*height)+" square(s)!");
        }

        MinesweeperBoardModel mbm = new MinesweeperBoardModel(width, height);

        int x, y;
        while(mbm.getNumberOfMines()<numberOfMines) {
            x = random.nextInt(width);
            y = random.nextInt(height);

            mbm.mine(x, y);
        }

        return mbm;
    }
}
