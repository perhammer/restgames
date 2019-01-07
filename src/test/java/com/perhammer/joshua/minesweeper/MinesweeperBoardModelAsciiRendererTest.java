package com.perhammer.joshua.minesweeper;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.fail;


public class MinesweeperBoardModelAsciiRendererTest {

    private MinesweeperBoardModel mbm;
    private MinesweeperController mc;
    private MinesweeperBoardModelAsciiRenderer renderer;

    @Test
    public void testOutput() throws MinesweeperGameLostException {
        mbm = new MinesweeperBoardModel(3,9);
        mbm.mine(2,5);

        Random random = new Random(-4830439552724388279L);

        mbm = new MinesweeperBoardModelFactory(random).getBeginnerBoard();

        renderer = new MinesweeperBoardModelAsciiRenderer(mbm);
        mc = new MinesweeperController(mbm);

        reveal(0,0);
        reveal(2,3);
        reveal(3,4);
        reveal(6,0);
        reveal(5,4);
        reveal(7,7);
        reveal(7,4);
        reveal(7,3);
        reveal(2,5);
        reveal(0,7);
        reveal(7,0);
        reveal(7,2);
        reveal(1,5);
        reveal(2,7);
        reveal(6,2);
        reveal(7,1);

        mark(2,2);
        mark(2,4);
        mark(6,1);
        mark(6,3);
        mark(4,4);
        mark(6,4);
        mark(7,5);
        mark(3,5);
        mark(0,5);
        mark(2,6);



    }

    private void unmark(int x, int y) {
        mc.unmark(x,y);
        renderer.render(System.out);
    }

    private void reveal(int x, int y) {
        boolean gameOn=true;
        try {
            mc.reveal(x,y);
        } catch (MinesweeperGameLostException | MinesweeperGameWonException e) {
            gameOn = false;
        }
        renderer.render(System.out);
        if(!gameOn) {
            fail("Game Over");
        }
    }

    private void mark(int x, int y) {
        mc.mark(x,y);
        renderer.render(System.out);
    }
}
