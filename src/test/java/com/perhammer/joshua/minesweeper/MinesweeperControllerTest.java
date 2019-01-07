package com.perhammer.joshua.minesweeper;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class MinesweeperControllerTest {

    @Test
    public void testThatMineGetsReallocatedIfHitOnFirstMove() {
        MinesweeperBoardModel mbm = new MinesweeperBoardModelFactory().getCustomBoard(1, 2, 0);

        mbm.mine(0 ,0);


        assertTrue(mbm.isMined(0, 0));
        assertFalse(mbm.isMined(0, 1));

        MinesweeperController mc = new MinesweeperController(mbm);

        try {
            mc.reveal(0,0);
        } catch (MinesweeperGameLostException | MinesweeperGameWonException e) {
            fail(e.getMessage());
        }

        assertFalse(mbm.isMined(0, 0));
        assertTrue(mbm.isMined(0, 1));

        try {
            mc.reveal(0,1);
            fail("Should have Game Over'ed by now...");
        } catch (MinesweeperGameLostException | MinesweeperGameWonException e) {
            // success
        }
    }
}
