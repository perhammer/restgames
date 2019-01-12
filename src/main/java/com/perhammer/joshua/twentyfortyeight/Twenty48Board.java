package com.perhammer.joshua.twentyfortyeight;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Twenty48Board {

    private static final int BOARD_SIZE = 4;
    private static final List<Integer> TILE_VALUES = new ArrayList<Integer>(){
        {
            add(4);
            while (size()<10) {
                add(2);
            }
        }
    };

    private ArrayList<List<Integer>> board;

    private Random random;
    private int score;

    public Twenty48Board() {
        this(new Random());
    }

    public Twenty48Board(Random random) {
        this.random = random;
        this.board = new ArrayList<>(BOARD_SIZE);
        for(int i =0; i!=BOARD_SIZE; i++) {
            List<Integer> row = new ArrayList<>(BOARD_SIZE);
            for(int j=0; j!=BOARD_SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
    }

    public void addTile() throws GameOverException {
        List<Integer> twosAndFours = new ArrayList<Integer>(){
            {
                addAll(TILE_VALUES);
            }
        };
        Collections.shuffle(twosAndFours, random);
        int newTileValue = twosAndFours.get(0);

        List<List<Integer>> emptyGridPositions = getXYCoordinatesOfAllEmptyTiles();

        if(emptyGridPositions.size()==0) {
            throw new GameOverException("No more empty grid positions.");
        }

        Collections.shuffle(emptyGridPositions, random);

        List<Integer> addTileAt = emptyGridPositions.get(0);

        int columnCounter = addTileAt.get(0);
        int rowCounter = addTileAt.get(1);

        board.get(rowCounter).set(columnCounter, newTileValue);
        this.score += newTileValue;
    }

    public void slideUp() {
        for(int i=0; i!=BOARD_SIZE; i++) {
            List<Integer> column = getColumn(i);
            column = slideLeft(column);
            setColumn(i, column);
        }
    }

    public void slideDown() {
        for(int i=0; i!=BOARD_SIZE; i++) {
            List<Integer> column = getColumn(i);
            Collections.reverse(column);
            column = slideLeft(column);
            Collections.reverse(column);
            setColumn(i, column);
        }
    }


    public void slideLeft() {
        for(int i=0; i!=BOARD_SIZE; i++) {
            List<Integer> row = getRow(i);
            row = slideLeft(row);
            setRow(i, row);
        }
    }

    public void slideRight() {
        for(int i=0; i!=BOARD_SIZE; i++) {
            List<Integer> row = getRow(i);
            Collections.reverse(row);
            row = slideLeft(row);
            Collections.reverse(row);
            setRow(i, row);
        }
    }

    private List<Integer> slideLeft(List<Integer> oldList) {
        NumberFusingStack nfs = new NumberFusingStack();

        for(Integer i:oldList) {
            if (i>0) {
                nfs.push(i);
                if(nfs.peek()!=i) {
                    score += nfs.peek();
                    nfs.push(NumberFusingStack.UNFUSEABLE);
                }
            }
        }

        List<Integer> newList = nfs.subList(0, nfs.size());
        while(newList.size()!=oldList.size()) {
            newList.add(0);
        }

        return newList;
    }

    private List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();

        row.addAll(board.get(rowIndex));

        return row;
    }

    private void setRow(int rowIndex, List<Integer> row) {
        if (row.size()!=board.get(rowIndex).size()) {
            throw new IllegalArgumentException("Unable to replace row, supplied width is "+row.size()+", expected width is "+board.get(rowIndex).size());
        }

        board.set(rowIndex, row);
    }

    private List<Integer> getColumn(int columnIndex) {
        List<Integer> column = new ArrayList<>();

        for(int i=0; i!=BOARD_SIZE; i++) {
            column.add(board.get(i).get(columnIndex));
        }

        return column;
    }

    private void setColumn(int columnIndex, List<Integer> column) {
        if (column.size()!=board.size()) {
            throw new IllegalArgumentException("Unable to replace column, supplied height is "+column.size()+", expected height is "+board.size());
        }

        for(int i=0; i!=board.size(); i++) {
            board.get(i).set(columnIndex, column.get(i));
        }
    }

    private List<List<Integer>> getXYCoordinatesOfAllEmptyTiles() {
        List<List<Integer>> emptyTiles = new ArrayList<>();

        for(int rowCounter=0; rowCounter!=BOARD_SIZE; rowCounter++) {
            for(int columnCounter=0; columnCounter!=BOARD_SIZE; columnCounter++) {
                if(0==board.get(rowCounter).get(columnCounter)) {
                    int finalColumnCounter = columnCounter;
                    int finalRowCounter = rowCounter;
                    emptyTiles.add(new ArrayList<Integer>(){
                        {
                            add(finalColumnCounter);
                            add(finalRowCounter);
                        }
                    });
                }
            }
        }
        return emptyTiles;
    }

    public int getScore() {
        return this.score;
    }

    public void render(PrintStream out) {
        for(int rowCounter=0; rowCounter!=board.size(); rowCounter++) {
            for(int columnCounter=0; columnCounter!=board.get(rowCounter).size(); columnCounter++) {
                out.print(board.get(rowCounter).get(columnCounter)+" ");
            }
            out.println();
        }
    }

    public ArrayList<List<Integer>> getBoard() {
        return board;
    }
}
