package com.perhammer.joshua.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinesweeperBoardModelFactoryTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThatTryingToAddMoreMinesThanThereAreSquaresThrowsAnException() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getCustomBoard(1,1,2);
    }

    @Test(timeout = 10L)
    public void testThatTryingToAddFullCoverageOfMinesIsOk() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getCustomBoard(2,2,4);
        assertEquals(4, mbm.getNumberOfMines());

    }

    @Test(timeout = 10000L)
    public void testThatCreatingSnakeBoardCompletesInASensibleTime() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getSnakeBoard();

        assertEquals(9999, mbm.getNumberOfMines());
    }
}
