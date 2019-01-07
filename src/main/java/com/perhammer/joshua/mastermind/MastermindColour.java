package com.perhammer.joshua.mastermind;

import java.util.ArrayList;
import java.util.Collection;

public enum MastermindColour {
    BLUE, GREEN, ORANGE, PURPLE, RED, YELLOW, DUMMY;
    public static final Collection<? extends MastermindColour> ALL_VALID_COLOURS = new ArrayList<MastermindColour>() {
        {
            add(BLUE);
            add(GREEN);
            add(ORANGE);
            add(PURPLE);
            add(RED);
            add(YELLOW);
        }
    };
}
