package com.perhammer.joshua.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinesweeperBoardTest {

    @Test
    public void testSmallBoard() {
        MinesweeperBoard board = MinesweeperBoard.getBeginnerBoard();
    }

    @Test
    public void testThatGeneratedBoardHasTheCorrectNumberOfMines() {
        MinesweeperBoard board;
        board = new MinesweeperBoard(10,10,10);
        assertEquals(board.getMineCount(),10);
    }

    @Test
    public void testThatFirstOpenIsNeverMined() {
        MinesweeperBoard board;
        board = new MinesweeperBoard(1,1,1);
        assertEquals(board.getMineCount(),1);
//        System.out.println(board.getView());

        board.open(0,0);

        assertEquals(board.getMineCount(),0);

//        System.out.println(board.getView());

        board = new MinesweeperBoard(2,1, 1);
        assertEquals(1, board.getMineCount());
        board.open(0,0);
        System.out.println(board.getView());
        assertEquals(1, board.getMineCount());
    }

    @Test
    public void testThatOpens() {
        MinesweeperBoard board = new MinesweeperBoard(10,10, 1);
        System.out.println(board.getView());
        board.open(0,0);
        System.out.println(board.getView());
    }
}
