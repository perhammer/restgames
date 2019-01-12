package com.perhammer.joshua.minesweeper;

import com.perhammer.joshua.puzzlegames.PuzzleGameController;

import java.util.Random;

public class MinesweeperController extends PuzzleGameController {

    private static final String GAMENAME = "Minesweeper";

    private MinesweeperBoardModel boardModel;
    private boolean firstMoveMade;

    public MinesweeperController(MinesweeperBoardModel boardModel) {
        this(boardModel, new Random());
    }

    public MinesweeperController(MinesweeperBoardModel boardModel, Random random) {
        super(random);

        this.boardModel = boardModel;
        this.firstMoveMade = false;
    }

    public void mark(int x, int y) {
        if(boardModel.isMarked(x,y)) {
            return;
        }
        moveCounter++;
        boardModel.mark(x,y);
    }

    public void unmark(int x, int y) {
        if(!boardModel.isMarked(x,y)) {
            return;
        }
        moveCounter++;
        boardModel.unmark(x,y);
    }

    public void reveal(int x, int y) throws MinesweeperGameLostException, MinesweeperGameWonException {
        if(boardModel.isUncovered(x,y)) {
            return;
        }

        moveCounter++;

        boardModel.uncover(x, y);

        if(boardModel.isMined(x,y)) {
            if(!firstMoveMade) {
                boardModel.unmine(x,y);
                // add a mine at (0,0), (0,1), etc.
                int minesToPlace = 1;
                for(int i=0; i!=boardModel.getWidth(); i++) {
                    for(int j=0; j!=boardModel.getHeight(); j++) {
                        if(!boardModel.isMined(i,j) && !boardModel.isUncovered(i,j) && minesToPlace>0) {
                            boardModel.mine(i,j);
                            minesToPlace--;
                        }
                    }
                }
            } else {
                super.finished();
                throw new MinesweeperGameLostException();
            }
        }

        firstMoveMade = true;

        if (boardModel.getNumberOfNeighbouringMines(x,y)==0) {
            tryRevealing(x-1, y-1);
            tryRevealing(x-1, y-0);
            tryRevealing(x-1, y+1);
            tryRevealing(x-0, y-1);
            tryRevealing(x-0, y+1);
            tryRevealing(x+1, y-1);
            tryRevealing(x+1, y-0);
            tryRevealing(x+1, y+1);
        }

        if (isGameWon()) {
            super.finished();
            throw new MinesweeperGameWonException();
        }
    }

    @Override
    public void scramble() {
        // nop
    }

    @Override
    public String getGameVariation() {
        return GAMENAME+boardModel.getWidth()+"x"+boardModel.getHeight()+"x"+boardModel.getNumberOfMines();
    }

    private boolean isGameWon() {
        // "The game is won when all mine-free squares are revealed"

        for (int y = 0; y != boardModel.getHeight(); y++) {
            for(int x=0; x!=boardModel.getWidth(); x++) {
                if (!boardModel.isMined(x,y) && !boardModel.isUncovered(x,y)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void tryRevealing(int x, int y) throws MinesweeperGameLostException, MinesweeperGameWonException {
        try {
            reveal(x, y);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // ignore
        }
    }

    public MinesweeperBoardModel getBoard() {
        return this.boardModel;
    }
}
