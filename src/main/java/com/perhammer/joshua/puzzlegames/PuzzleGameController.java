package com.perhammer.joshua.puzzlegames;

import com.perhammer.joshua.twentyfortyeight.GameOverException;

import java.util.Random;

public abstract class PuzzleGameController {

    protected Random random;
    protected int moveCounter;
    protected boolean isFinished;
    private long startedAtMillis;
    private long finishedAtMillis;
    private Long moveCount;

    public PuzzleGameController() {
        this(new Random());
    }

    public PuzzleGameController(Random random) {
        this.random = random;
        this.isFinished = false;
        this.startedAtMillis = System.currentTimeMillis();
        this.finishedAtMillis = 0;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public long getGameDuration() {
        long duration;

        if (finishedAtMillis==0) {
            duration = System.currentTimeMillis() - startedAtMillis;
        } else {
            duration = finishedAtMillis - startedAtMillis;
        }
        return duration;
    }

    public abstract void scramble();

    protected void finished() {
        this.isFinished = true;
        this.finishedAtMillis = System.currentTimeMillis();
    }

    protected void throwGameOverExceptionIfGameIsFinished() {
        if (isFinished) {
            throw new RuntimeException(new GameOverException("This game has ended, no further moves possible"));
        }
    }

    public Long getMoveCount() {
        return moveCount;
    }

    public abstract String getGameVariation();
}
