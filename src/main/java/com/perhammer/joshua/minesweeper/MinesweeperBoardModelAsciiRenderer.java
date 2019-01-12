package com.perhammer.joshua.minesweeper;

import java.io.PrintStream;

public class MinesweeperBoardModelAsciiRenderer {

    public static final char TOP_LEFT_CORNER = '╔';
    public static final char TOP_RIGHT_CORNER = '╗';
    public static final char HORIZONTAL_BAR = '═';
    public static final char VERTICAL_BAR = '║';
    public static final char BOTTOM_LEFT_CORNER = '╚';
    public static final char BOTTOM_RIGHT_CORNER = '╝';
    public static final char FLAG = '!';
    public static final char FOG = '░';
    public static final char MINE = 'Ó';
    public static final char CLEARED = ' ';


    private final MinesweeperBoardModel boardModel;

    public MinesweeperBoardModelAsciiRenderer(MinesweeperBoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void render(PrintStream out) {
        out.println("Mines left: "+(boardModel.getNumberOfMines()-boardModel.getNumberOfFlags())+" (Mines: "+boardModel.getNumberOfMines()+", Marks: "+boardModel.getNumberOfFlags()+")");
        out.print(TOP_LEFT_CORNER);
        for(int i=0; i!=boardModel.getWidth(); i++) {
            out.print(HORIZONTAL_BAR);
        }
        out.println(TOP_RIGHT_CORNER);

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
            out.println(VERTICAL_BAR);
        }

        out.print(BOTTOM_LEFT_CORNER);
        for(int i=0; i!=boardModel.getWidth(); i++) {
            out.print(HORIZONTAL_BAR);
        }
        out.println(BOTTOM_RIGHT_CORNER);

    }
}
