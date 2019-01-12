package com.perhammer.joshua.twentyfortyeight;

import com.perhammer.joshua.puzzlegames.PuzzleGameController;

import java.io.PrintStream;
import java.util.List;
import java.util.Random;

public class Twenty48Controller extends PuzzleGameController {

    private static final String GAMENAME = "2048";
    private final Twenty48Board board;

    public Twenty48Controller() {
        this(new Random());
    }

    public Twenty48Controller(Random random) {
        super(random);

        this.board = new Twenty48Board(random);

        try {
            board.addTile();
            board.addTile();
        } catch (GameOverException e) {
            throw new RuntimeException("Badly configured board? ", e);
        }
    }

    public void slideUp() {
        throwGameOverExceptionIfGameIsFinished();
        moveCounter++;
        board.slideUp();
        tryToAddTile();
    }

    public void slideDown() {
        throwGameOverExceptionIfGameIsFinished();
        moveCounter++;
        board.slideDown();
        tryToAddTile();
    }

    public void slideLeft() {
        throwGameOverExceptionIfGameIsFinished();
        moveCounter++;
        board.slideLeft();
        tryToAddTile();
    }

    public void slideRight() {
        throwGameOverExceptionIfGameIsFinished();
        moveCounter++;
        board.slideRight();
        tryToAddTile();
    }

    public int getScore() {
        return board.getScore();
    }

    public List<List<Integer>> getBoard() {
        return board.getBoard();
    }

    public void render(PrintStream out) {
        this.board.render(out);
    }

    @Override
    public void scramble() {
        // nop
    }

    @Override
    public String getGameVariation() {
        return GAMENAME;
    }

    private void tryToAddTile() {
        try {
            board.addTile();
        } catch (GameOverException e) {
            super.finished();
        }
    }

}
