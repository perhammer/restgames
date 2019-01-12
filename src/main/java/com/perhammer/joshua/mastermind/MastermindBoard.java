package com.perhammer.joshua.mastermind;

import java.util.ArrayList;
import java.util.List;

public class MastermindBoard {
    private List<MastermindColour> challenge;
    private List<List<MastermindColour>> attempts;
    private List<List<MastermindHint>> hints;

    /*
    has a challenge row (4 slots, each a choice from 6)
    has a list of attempt rows (4 slots, each a choice from 6, 4 hint slots, each a choice from 2)
     */

    public MastermindBoard() {
        this.attempts = new ArrayList<>();
        this.hints = new ArrayList<>();
    }

    public void setChallenge(List<MastermindColour> challenge) {
        this.challenge = new ArrayList<>();
        this.challenge.addAll(challenge);
    }

    public List<MastermindHint> addAttempt(List<MastermindColour> attempt) {
        attempts.add(attempt);
        List<MastermindColour> checkColours = new ArrayList<>();
        checkColours.addAll(challenge);

        List<MastermindHint> hint = new ArrayList<>();

        for(int i=0; i!=attempt.size();i ++) {
            if (attempt.get(i) == checkColours.get(i)) {
                hint.add(MastermindHint.CORRECT_COLOUR_AND_LOCATION);
                checkColours.set(i, MastermindColour.DUMMY);
            } else if (checkColours.contains(attempt.get(i))) {
                hint.add(MastermindHint.CORRECT_COLOUR);
                checkColours.set(checkColours.indexOf(attempt.get(i)), MastermindColour.DUMMY);
            }
        }

        hints.add(hint);
        return hint;
    }

    public int getChallengeWidth() {
        return challenge.size();
    }
}
