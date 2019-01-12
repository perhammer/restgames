package com.perhammer.joshua.restgames;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.perhammer.joshua.mastermind.MastermindHint;

import java.util.Collections;
import java.util.List;

public class MastermindState {

    private final String gameId;
    @JsonIgnore
    private boolean finished;
    private String code;
    private List<MastermindHint> hint;


    public MastermindState(int gameId) {
        this.gameId = gameId+"";
        this.code = "XXXX";
        this.hint = Collections.emptyList();
        this.finished = false;
    }

    public String getGameId() {
        return gameId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setHint(List<MastermindHint> hint) {
        this.hint = hint;
    }

    public List<MastermindHint> getHint() {
        return hint;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
