package com.perhammer.joshua.restgames;

import com.perhammer.joshua.slidingpuzzle.SlidingMove;
import com.perhammer.joshua.slidingpuzzle.SlidingPuzzleBoard;

import java.util.List;

public class SlideState {

    private String gameId;
    private SlidingPuzzleBoard board;
    private List<SlidingMove> moves;

    public SlideState(Integer gameId, SlidingPuzzleBoard spb) {
        this.gameId = gameId+"";
        this.board = spb;
    }

    public void setMoves(List<SlidingMove> moves) {
        this.moves = moves;
    }

    public List<SlidingMove> getMoves() {
        return this.moves;
    }

    public String getGameId() {
        return gameId;
    }

    public SlidingPuzzleBoard getBoard() {
        return board;
    }
}
