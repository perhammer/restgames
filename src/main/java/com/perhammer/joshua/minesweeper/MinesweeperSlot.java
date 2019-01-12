package com.perhammer.joshua.minesweeper;

public class MinesweeperSlot {

    private boolean visible;
    private boolean mined;
    private boolean flagged;
    private int numberOfMinedNeighbours;

    public MinesweeperSlot() {
        this.visible = false;
        this.mined = false;
        this.flagged = false;
        this.numberOfMinedNeighbours = 0;
    }

    void mine() {
        this.mined = true;
    }

    void uncover() {
        this.visible = true;
    }

    void mark() {
        this.flagged = true;
    }
    public void unmark() {
        this.flagged = false;
    }


    public boolean isMined() {
        return mined;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getNumberOfMinedNeighbours() {
        return numberOfMinedNeighbours;
    }

    public boolean isMarked() {
        return flagged;
    }

    public void incrementNumberOfMinedNeighbours() {
        this.numberOfMinedNeighbours++;
    }

    public void unmine() {
        this.mined = false;
    }

    public void decrementNumberOfMinedNeighbours() {
        this.numberOfMinedNeighbours--;
    }
}
