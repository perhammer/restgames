package com.perhammer.joshua.minesweeper;

import java.util.Random;

public class MinesweeperBoard {

    private final char VIEW_UNOPENED = '≡';
    private final char VIEW_MINED = 'Ó';

    private final int MINED = 1;
    private final int UNMINED = 0;

    /*
    Full rules: https://zyxyvy.wordpress.com/2012/08/11/the-rules-of-minesweeper/


    2d array of slots
    each slot can be:
    - unexplored
    - explored (showing the number of mines in immediately adjacent slots (0-8))
    - mined

    Additionally, each slot can be marked:
    - potentially mined

    The first square never contains a mine (if it did, it's moved to (0,0), then (0,1) etc. until a slot is found).

    Beginner board has 10 mines on an 8-by-8 board
    Intermediate board has 40 mines on a 16-by-16 board
    Expert board has 99 mines on a 16-by-30 board.

     */

    public static MinesweeperBoard getBeginnerBoard() {
        return new MinesweeperBoard(8, 8, 10);
    }
    public static MinesweeperBoard getIntermediateBoard() {
        return new MinesweeperBoard(16, 16, 40);
    }
    public static MinesweeperBoard getExpertBoard() {
        return new MinesweeperBoard(16, 30, 99);
    }

    private int[][] slots;
    private boolean[][] visible;
    boolean firstClickHasBeenMade;

    public MinesweeperBoard(int width, int height, int mineCount) {
        System.out.println("Creating board "+width+"x"+height);
        if(width<1 || height<1 || mineCount<1 || mineCount>(width*height)) {
            throw new RuntimeException("Invalid board configuration!");
        }

        firstClickHasBeenMade = false;
        slots = new int[width][height];
        visible = new boolean[width][height];

        for(int i=0; i!=width; i++) {
            for(int j=0; j!=height; j++) {
                slots[i][j]= UNMINED;
                visible[i][j]= false;
            }
        }

        Random r = new Random();
        for (int i=0; i!=mineCount; i++) {
            int x, y;
            do {
                x = r.nextInt(width);
                y = r.nextInt(height);
            } while (slots[x][y]==1);
            slots[x][y]=MINED;
        }

        System.out.println("Placed "+getMineCount()+" mine(s)");
    }

    public String getView() {
        StringBuilder sb = new StringBuilder(visible.length+1 * visible[0].length);
        for(int i=0; i!=slots.length; i++) {
            for(int j=0; j!=slots[i].length; j++) {
                if (!visible[i][j]) {
                    sb.append(VIEW_UNOPENED);
                } else if (slots[i][j]==MINED) {
                    sb.append(VIEW_MINED);

                } else {
                    sb.append(getNumberOfMinedNeighbours(i,j));
                }
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public int getMineCount() {
        int mineCount = 0;
        for(int i=0; i!=slots.length; i++) {
            for(int j=0; j!=slots[i].length; j++) {
                mineCount = mineCount + slots[i][j];
            }
        }
        return mineCount;
    }

    private int getNumberOfMinedNeighbours(int x, int y) {
        int numberOfMinedNeighbours = 0;
        numberOfMinedNeighbours += isMined(x-1, y-1);
        numberOfMinedNeighbours += isMined(x, y-1);
        numberOfMinedNeighbours += isMined(x+1, y-1);
        numberOfMinedNeighbours += isMined(x-1, y);
        numberOfMinedNeighbours += isMined(x+1, y);
        numberOfMinedNeighbours += isMined(x-1, y+1);
        numberOfMinedNeighbours += isMined(x, y+1);
        numberOfMinedNeighbours += isMined(x+1, y+1);

        return numberOfMinedNeighbours;
    }

    private int isMined(int x, int y) {
        try {
            return slots[x][y];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return 0;
        }
    }

    public void open(int x, int y) {
        try {
            int tmp = slots[x][y];
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return;
        }
        if (visible[x][y]) {
            return;
        }

        System.out.println("Opening "+x+", "+y+" (Mined? "+slots[x][y]+")");

        if (!firstClickHasBeenMade && slots[x][y]== MINED) {
            System.out.println("Moving mine ...");
            slots[x][y]=UNMINED; // uncovered, unmined
            // try to move mine
            for(int i=0; i!=slots.length; i++) {
                for(int j=0; j!=slots[0].length; j++) {
                    System.out.println("Trying "+i+", "+j+" (Mined? "+slots[i][j]+") (Me? "+(i==x && j==y)+")");
                    if (!(i==x && j==y)) {
                        if (slots[i][j]==UNMINED) {
                            slots[i][j]=MINED;
                            System.out.println("Moved mine to "+i+", "+j+". Twenty48Board is "+slots.length+"x"+slots[0].length);
                            break;
                        }
                    }
                }
            }
        }
        firstClickHasBeenMade = true;

        // TODO
        if (slots[x][y]==MINED) {
            // GAME OVER
            System.out.println("GAME OVER!");
//            System.exit(0);
        }

        visible[x][y]=true;
        if (getNumberOfMinedNeighbours(x,y)==0) {
            open(x-1, y-1);
            open(x-1, y);
            open(x-1, +1);
            open(x, y-1);
            open(x, y+1);
            open(x+1, y-1);
            open(x+1, y);
            open(x+1, y+1);
            System.out.println();

        }
    }
}
