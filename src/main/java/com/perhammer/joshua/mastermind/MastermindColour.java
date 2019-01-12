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

    public static MastermindColour fromString(String line) {
        if (line==null || line.length()<1) {
            throw new IllegalArgumentException("Unable to determine");
        }

        return fromString(line.charAt(0));
    }

    public static MastermindColour fromString(char ch) {

        switch(ch) {
            case 'B':
            case 'b':
                return MastermindColour.BLUE;
            case 'G':
            case 'g':
                return MastermindColour.GREEN;
            case 'O':
            case 'o':
                return MastermindColour.ORANGE;
            case 'P':
            case 'p':
                return MastermindColour.PURPLE;
            case 'R':
            case 'r':
                return MastermindColour.RED;
            case 'Y':
            case 'y':
                return MastermindColour.YELLOW;
            default:
                throw new IllegalArgumentException("Unable to determine: "+ch);
        }
    }
}
