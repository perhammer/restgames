package com.perhammer.joshua.restgames;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perhammer.joshua.minesweeper.MinesweeperBoardModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinesweeperState {

    private static final char FLAG = '!';
    private static final char FOG = 'X';
    private static final char MINE = '*';
    private static final char CLEARED = ' ';
    private final String gameId;
    private MinesweeperBoardModel board;
    @JsonIgnore
    private boolean finished;

    public MinesweeperState(Integer gameId) {
        this.gameId = gameId+"";
        this.finished = false;
    }

    public String getGameId() {
        return gameId;
    }

    public void setBoard(MinesweeperBoardModel mbm) {
        this.board = mbm;
    }

    public MinesweeperBoardModel getBoard() {
        return board;
    }

    public List<List<Character>> getBoardView() {
        if (this.board==null) {
            return Collections.emptyList();
        }

        List<List<Character>> view = new ArrayList<>();
        for(int y=0; y!=board.getHeight(); y++) {
            List<Character> row = new ArrayList<>();
            for(int x=0; x!=board.getWidth(); x++) {
                char c;
                if(board.isMarked(x,y)) {
                    c = FLAG;
                } else if (!board.isUncovered(x,y)) {
                    c = FOG;
                } else {
                    if (board.isMined(x,y)) {
                        c = MINE;
                    } else {
                        int numberOfNeighbouringMines = board.getNumberOfNeighbouringMines(x,y);
                        if (numberOfNeighbouringMines==0) {
                            c = CLEARED;
                        } else {
                            c = (numberOfNeighbouringMines+"").charAt(0);
                        }
                    }
                }
                row.add(c);
            }
            view.add(row);
        }

        return view;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return this.finished;
    }

    /*
    for(int y=0; y!=boardModel.getHeight(); y++) {
            out.print(VERTICAL_BAR);
            for(int x=0; x!=boardModel.getWidth(); x++) {
                if(boardModel.isMarked(x,y)) {
                    out.print(FLAG);
                } else if(!boardModel.isUncovered(x,y)) {
                    out.print(FOG);
                } else {
                    if (boardModel.isMined(x,y)) {
                        out.print(MINE);
                    } else {
                        int numberOfNeighbouringMines = boardModel.getNumberOfNeighbouringMines(x,y);
                        if(numberOfNeighbouringMines==0) {
                            out.print(CLEARED);
                        } else {
                            out.print(numberOfNeighbouringMines);
                        }
                    }
                }
            }
     */
}
