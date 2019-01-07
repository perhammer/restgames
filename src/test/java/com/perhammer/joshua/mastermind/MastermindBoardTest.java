package com.perhammer.joshua.mastermind;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MastermindBoardTest {

    @Test
    public void testHintLogic() {
        MastermindBoard mb = new MastermindBoard();
        mb.setChallenge(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.BLUE);
            }
        });

        List<MastermindHint> hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.ORANGE);
            }
        });

        assertTrue(hint.size()==0);

        hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.BLUE);
            }
        });

        assertTrue(hint.size()==1);
        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
    }

    @Test
    public void testHintLogicForDuplicatedColours() {
        MastermindBoard mb = new MastermindBoard();
        mb.setChallenge(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.BLUE);
                add(MastermindColour.BLUE);
                add(MastermindColour.RED);
                add(MastermindColour.RED);

            }
        });
        List<MastermindHint> hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.BLUE);
                add(MastermindColour.BLUE);
                add(MastermindColour.BLUE);
                add(MastermindColour.RED);
            }
        });

        assertEquals(3, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(2) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
    }

    @Test
    public void testGame() {
        MastermindBoard mb = new MastermindBoard();
        mb.setChallenge(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.PURPLE);
                add(MastermindColour.BLUE);
                add(MastermindColour.PURPLE);
                add(MastermindColour.GREEN);
            }
        });

        List<MastermindHint> hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.BLUE);
                add(MastermindColour.GREEN);
                add(MastermindColour.ORANGE);
                add(MastermindColour.PURPLE);
            }
        });

        assertEquals(3, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR);
        assertTrue( hint.get(2) == MastermindHint.CORRECT_COLOUR);

        hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.ORANGE);
                add(MastermindColour.GREEN);
                add(MastermindColour.BLUE);
                add(MastermindColour.BLUE);
            }
        });

        assertEquals(2, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR);

        hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.ORANGE);
                add(MastermindColour.PURPLE);
                add(MastermindColour.GREEN);
                add(MastermindColour.BLUE);
            }
        });

        assertEquals(3, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR);
        assertTrue( hint.get(2) == MastermindHint.CORRECT_COLOUR);

        hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.RED);
                add(MastermindColour.BLUE);
                add(MastermindColour.PURPLE);
                add(MastermindColour.GREEN);
            }
        });

        assertEquals(3, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(2) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);

        hint = mb.addAttempt(new ArrayList<MastermindColour>() {
            {
                add(MastermindColour.PURPLE);
                add(MastermindColour.BLUE);
                add(MastermindColour.PURPLE);
                add(MastermindColour.GREEN);
            }
        });

        assertEquals(4, hint.size());

        assertTrue( hint.get(0) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(1) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(2) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
        assertTrue( hint.get(3) == MastermindHint.CORRECT_COLOUR_AND_LOCATION);
    }
}
