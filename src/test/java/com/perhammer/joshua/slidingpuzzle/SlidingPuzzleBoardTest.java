package com.perhammer.joshua.slidingpuzzle;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlidingPuzzleBoardTest {

    @Test
    public void testBasicSetup() {
        String[][] tiles = getXbyYBoard(1,1);
        SlidingPuzzleBoard spb = new SlidingPuzzleBoard(tiles);
        SlidingPuzzleBoardAsciiRenderer renderer = new SlidingPuzzleBoardAsciiRenderer(spb);

        String output = renderer.render();

        assertEquals("1"+System.lineSeparator(), output);
    }

    @Test
    public void testProperFormattingOf3x3Board() {
        String[][] tiles = getXbyYBoard(3,3);
        tiles[2][2] = "";
        SlidingPuzzleBoard spb = new SlidingPuzzleBoard(tiles);
        SlidingPuzzleBoardAsciiRenderer renderer = new SlidingPuzzleBoardAsciiRenderer(spb);

        String output = renderer.render();

        assertEquals(
                "1|2|3"+System.lineSeparator()+ "-----"+System.lineSeparator()+
                "4|5|6"+System.lineSeparator()+ "-----"+System.lineSeparator()+
                "7|8|"+System.lineSeparator(),
                output);
    }

    private String[][] getXbyYBoard(int x, int y) {
        String[][] board = new String[y][x];
        for(int yy=0; yy!=y; yy++) {
            for(int xx=0; xx!=x; xx++) {
                board[yy][xx] = ""+(yy*y+xx+1);
            }
        }
        return board;
    }

    // 1 2 3        1 2 3
    // 4 . 5   -->  4 8 5
    // 7 8 6        7 . 6



    // 0 1 2   |     -size
    // 3 4 5   |  -1        +1
    // 6 7 8   |     +size


}
