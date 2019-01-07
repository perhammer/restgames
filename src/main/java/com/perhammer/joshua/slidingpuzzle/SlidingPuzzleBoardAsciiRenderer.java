package com.perhammer.joshua.slidingpuzzle;

import java.io.PrintStream;
import java.util.List;

public class SlidingPuzzleBoardAsciiRenderer {

    private PrintStream out;
    private SlidingPuzzleBoard board;

    public SlidingPuzzleBoardAsciiRenderer() {

    }

    public SlidingPuzzleBoardAsciiRenderer(SlidingPuzzleBoard spb) {
        setBoard(spb);
    }

    public void setBoard(SlidingPuzzleBoard spb) {
        this.board = spb;
    }

    public String render() {
        StringBuilder output = new StringBuilder();
        String[][] values = board.getBoard();

        int fieldWidth = 0;
        int maxValue = values.length * values[0].length;
        while (maxValue>1) {
            fieldWidth++;
            maxValue = maxValue/10;
        }

        for(int y=0; y!=values.length; y++) {
            for(int x=0; x!=values[y].length; x++) {
                output.append(String.format("%1$"+fieldWidth+"s",values[y][x]));
                if (x!=values[y].length-1) {
                    output.append("|");
                }
            }
            output.append(System.lineSeparator());
            if(y!=values.length-1) {
                output.append(String.format("%1$"+((fieldWidth+1)*values[y].length-1)+"s","").replace(' ','-'));
                output.append(System.lineSeparator());
            }
        }

        return output.toString();
    }
}
