package com.perhammer.joshua.mastermind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MastermindController {

    private final MastermindBoard board;
    private Random random;

    private int moveCounter;
    private boolean isFinished;

    public MastermindController() {
        this(new Random());
    }

    public MastermindController(Random random) {
        this.random = random;
        this.board = new MastermindBoard();
        this.random = random;

        scramble();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void scramble() {
        List<MastermindColour> challenge = new ArrayList<>();

        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);

        Collections.shuffle(challenge, random);

        board.setChallenge(
                challenge.subList(0, 4)
        );
        this.moveCounter = 0;
        this.isFinished = false;
    }

    public List<MastermindHint> addAttempt(List<MastermindColour> attempt) {
        if (isFinished) {
            return new ArrayList<MastermindHint>() {
                {
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                }
            };
        }
        List<MastermindHint> hint = board.addAttempt(attempt);

        int pinsCorrect = 0;
        for(MastermindHint h:hint) {
            if (MastermindHint.CORRECT_COLOUR_AND_LOCATION.equals(h)) {
                pinsCorrect += 1;
            }
        }

        isFinished = (4 == pinsCorrect);

        Collections.shuffle(hint);

        return hint;
    }

}
