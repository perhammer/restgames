package com.perhammer.joshua.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinesweeperBoardModelTest {

    @Test
    public void testAsymmetricLayout() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModel(3,9);

        mbm.mine(0,0);
        mbm.mine(1,0);
        mbm.mine(2,0);

        mbm.mine(0,8);
        mbm.mine(1,8);
        mbm.mine(2,8);

        for(int y=0; y!=mbm.getHeight(); y++) {
            for(int x=0; x!=mbm.getWidth(); x++) {
                mbm.mine(x,y);
                mbm.isMined(x,y);
                mbm.isUncovered(x,y);
                mbm.mark(x,y);
                mbm.isMarked(x,y);
                mbm.unmine(x,y);
                mbm.uncover(x,y);
            }
        }
    }

    @Test
    public void testBasicFunctions() throws MinesweeperGameLostException {
        int width = 10;
        int height = 10;

        MinesweeperBoardModel mbm = new MinesweeperBoardModel(width, height);

        assertEquals(width, mbm.getWidth());
        assertEquals(height, mbm.getHeight());

        assertEquals(0, mbm.getNumberOfMines());
        assertEquals(0, mbm.getNumberOfFlags());

        assertEquals(0, mbm.getNumberOfNeighbouringMines(0,0));

        assertEquals(0, mbm.getNumberOfNeighbouringMines(0,1));
        assertEquals(0, mbm.getNumberOfNeighbouringMines(1,0));
        assertEquals(0, mbm.getNumberOfNeighbouringMines(1,1));


        mbm.mine(0,0);

        assertEquals(0, mbm.getNumberOfNeighbouringMines(0,0));

        assertEquals(1, mbm.getNumberOfNeighbouringMines(0,1));
        assertEquals(1, mbm.getNumberOfNeighbouringMines(1,0));
        assertEquals(1, mbm.getNumberOfNeighbouringMines(1,1));

        assertEquals(1, mbm.getNumberOfMines());

        mbm.mark(0,0);

        assertEquals(1, mbm.getNumberOfFlags());

        mbm.uncover(0,0);
    }
}
