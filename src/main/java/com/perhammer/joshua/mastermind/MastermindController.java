package com.perhammer.joshua.mastermind;

import com.perhammer.joshua.puzzlegames.PuzzleGameController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MastermindController extends PuzzleGameController {

    private static final String GAMENAME = "Mastermind/";
    private final MastermindBoard board;

    public MastermindController() {
        this(new Random());
    }

    public MastermindController(Random random) {
        super(random);
        this.board = new MastermindBoard();

        scramble();
    }

    @Override
    public void scramble() {
        List<MastermindColour> challenge = new ArrayList<>();

        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);
        challenge.addAll(MastermindColour.ALL_VALID_COLOURS);

        Collections.shuffle(challenge, super.random);

        board.setChallenge(
                challenge.subList(0, 4)
        );

        super.moveCounter = 0;
        super.isFinished = false;
    }

    @Override
    public String getGameVariation() {
        return GAMENAME+board.getChallengeWidth();
    }

    public List<MastermindHint> addAttempt(List<MastermindColour> attempt) {
        if (isFinished()) {
            return new ArrayList<MastermindHint>() {
                {
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                    add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                }
            };
        }

        super.moveCounter++;

        List<MastermindHint> hint = board.addAttempt(attempt);

        int pinsCorrect = 0;
        for(MastermindHint h:hint) {
            if (MastermindHint.CORRECT_COLOUR_AND_LOCATION.equals(h)) {
                pinsCorrect += 1;
            }
        }

        isFinished = (4 == pinsCorrect);
        if(isFinished) {
            super.finished();
        }

        Collections.shuffle(hint, super.random);

        return hint;
    }

    public MastermindBoard getBoard() {
        return board;
    }
}
