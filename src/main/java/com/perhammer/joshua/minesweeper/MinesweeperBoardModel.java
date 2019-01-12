package com.perhammer.joshua.minesweeper;

import java.util.*;

public class MinesweeperBoardModel {

    private MinesweeperSlot[][] slots;

    public MinesweeperBoardModel(int width, int height) {
        this.slots = new MinesweeperSlot[width][height];
        for(int i=0; i!=width; i++) {
            for(int j=0; j!=height; j++) {
                slots[i][j] = new MinesweeperSlot();
            }
        }
    }

    public int getWidth() {
        return slots.length;
    }

    public int getHeight() {
        return slots[0].length;
    }

    public void mine(int x, int y) {
        slots[x][y].mine();
        for(MinesweeperSlot neighbour:allNeighbouringSlots(x,y)) {
            neighbour.incrementNumberOfMinedNeighbours();
        }
    }

    public void unmine(int x, int y) {
        slots[x][y].unmine();
        for(MinesweeperSlot neighbour:allNeighbouringSlots(x,y)) {
            neighbour.decrementNumberOfMinedNeighbours();
        }
    }

    public boolean isMined(int x, int y) {
        return slots[x][y].isMined();
    }

    public void uncover(int x, int y) {
        slots[x][y].uncover();
    }

    public boolean isUncovered(int x, int y) {
        return slots[x][y].isVisible();
    }

    public void mark(int x, int y) {
        slots[x][y].mark();
    }

    public void unmark(int x, int y) {
        slots[x][y].unmark();
    }

    public boolean isMarked(int x, int y) {
        return slots[x][y].isMarked();
    }

    public int getNumberOfMines() {
        int numberOfMines = 0;
        for(MinesweeperSlot slot:allSlots()) {
            if(slot.isMined()) {
                numberOfMines++;
            }
        }
        return numberOfMines;
    }

    public int getNumberOfNeighbouringMines(int x, int y) {
        return slots[x][y].getNumberOfMinedNeighbours();
    }

    public int getNumberOfFlags() {
        int numberOfFlags = 0;
        for(MinesweeperSlot slot:allSlots()) {
            if(slot.isMarked()) {
                numberOfFlags++;
            }
        }
        return numberOfFlags;
    }

    private List<MinesweeperSlot> allNeighbouringSlots(int x, int y) {
        List<MinesweeperSlot> neighbouringSlots = new ArrayList<>();
        neighbouringSlots.addAll(getSlot(x-1, y-1));
        neighbouringSlots.addAll(getSlot(x-1, y-0));
        neighbouringSlots.addAll(getSlot(x-1, y+1));
        neighbouringSlots.addAll(getSlot(x-0, y-1));
        neighbouringSlots.addAll(getSlot(x-0, y+1));
        neighbouringSlots.addAll(getSlot(x+1, y-1));
        neighbouringSlots.addAll(getSlot(x+1, y-0));
        neighbouringSlots.addAll(getSlot(x+1, y+1));
        return neighbouringSlots;
    }

    private Collection<MinesweeperSlot> getSlot(int x, int y) {
        Collection<MinesweeperSlot> slots = new ArrayList<>();
        try {
            slots.add(this.slots[x][y]);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            // do nothing
        }
        return slots;
    }

    private List<MinesweeperSlot> allSlots() {
        List<MinesweeperSlot> listofSlots = new ArrayList<>();
        for(MinesweeperSlot[] rowOfSlots:this.slots) {
            listofSlots.addAll(Arrays.asList(rowOfSlots));
        }
        return listofSlots;
    }
}
