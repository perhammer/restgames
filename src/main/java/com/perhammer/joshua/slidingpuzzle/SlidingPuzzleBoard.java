package com.perhammer.joshua.slidingpuzzle;

public class SlidingPuzzleBoard {

    private int size;
    private String[][] tiles;

    public SlidingPuzzleBoard(String[][] tiles) {
        this.tiles = tiles;
    }

    public String[][] getBoard() {
        return this.tiles;
    }

}
