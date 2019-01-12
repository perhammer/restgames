package com.perhammer.joshua.restgames;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class TwentyfortyeightState {
    private int gameId;
    @JsonIgnore
    private boolean finished;
    private int score;
    private List<List<Integer>> board;

    public void setGameId(int gameId) {
        this.gameId = gameId;
        this.finished = false;
    }

    public String getGameId() {
        return gameId+"";
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }
}
