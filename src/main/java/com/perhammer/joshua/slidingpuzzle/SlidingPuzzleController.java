package com.perhammer.joshua.slidingpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SlidingPuzzleController {

    private int blankY;
    private int blankX;
    private int moveCounter;

    private static Random random;
    private SlidingPuzzleBoardAsciiRenderer renderer;

    public static SlidingPuzzleController get3by3game() {
        return getSquareGame(3);
    }
    public static SlidingPuzzleController get4by4game() {
        return getSquareGame(4);
    }
    public static SlidingPuzzleController get5by5game() {
        return getSquareGame(5);
    }

    private static SlidingPuzzleController getSquareGame(int i) {
        return new SlidingPuzzleController( i, i);
    }

    private SlidingPuzzleBoard board;

    private SlidingPuzzleController(int width, int height) {
        String[][] tiles = getXbyYBoard(width, height);

        this.blankX = width-1;
        this.blankY = height-1;

        tiles[this.blankY][this.blankX] = "";

        this.board = new SlidingPuzzleBoard(
                tiles
        );

        this.random = new Random();
    }

    public void setRenderer(SlidingPuzzleBoardAsciiRenderer renderer) {
        this.renderer = renderer;
        renderer.setBoard(board);
    }

    public SlidingPuzzleBoard getBoard() {
        return board;
    }

    public void scramble() {
        scramble(board);
        this.moveCounter = 0;
    }

    public void slide(SlidingMove direction) {
        if (isFinished()) {
            return;
        }
        if (getValidMoves().contains(direction.getOpposite())) {
            moveCounter++;
            move(direction.getOpposite());
        }
    }

    public boolean isFinished() {
        if (moveCounter==0) {
            return false;
        }
        int sequence = 1;
        for(int y=0; y!=board.getBoard().length; y++) {
            for(int x=0; x!=board.getBoard()[y].length; x++) {
                String actual = board.getBoard()[y][x];
                String expected = sequence+"";
                if(y==board.getBoard().length-1 && x==board.getBoard()[board.getBoard().length-1].length-1) {
                    expected="";
                }
                if(!actual.equals(expected)) {
                    return false;
                }
                sequence++;
            }
        }
        return true;
    }

    private void scramble(SlidingPuzzleBoard board) {
        SlidingMove lastMove = null;

        List<SlidingMove> moves;
        for(int i=0; i!=250; i++) {
            moves = getValidMoves();
            if (lastMove!=null && moves.contains(lastMove.getOpposite())) {
                moves.remove(lastMove.getOpposite());
            }

            Collections.shuffle(moves, random);

            SlidingMove direction = moves.get(0);
            move(direction);

            lastMove = direction;
        }
    }

    private void move(SlidingMove direction) {
        int targetBlankX=blankX;
        int targetBlankY=blankY;

        switch (direction) {
            case NORTH:
                targetBlankY -= 1;
                break;
            case SOUTH:
                targetBlankY += 1;
                break;
            case EAST:
                targetBlankX += 1;
                break;
            case WEST:
                targetBlankX -= 1;
                break;
            default:
                throw new RuntimeException("Invalid move, can't get opposite of "+direction);
        }

        String[][] tiles = board.getBoard();

        String tempValue = tiles[targetBlankY][targetBlankX];
        tiles[targetBlankY][targetBlankX]=tiles[blankY][blankX];
        tiles[blankY][blankX]= tempValue;
        blankY=targetBlankY;
        blankX=targetBlankX;
    }

    public List<SlidingMove> getValidMoves() {
        List<SlidingMove> moves = new ArrayList<>();

        if(!isBlankOnLeftEdge()) {
            moves.add(SlidingMove.WEST);
        }
        if(!isBlankOnRightEdge()) {
            moves.add(SlidingMove.EAST);
        }
        if(!isBlankOnTopEdge()) {
            moves.add(SlidingMove.NORTH);
        }
        if(!isBlankOnBottomEdge()) {
            moves.add(SlidingMove.SOUTH);
        }

        return moves;
    }

    private boolean isBlankOnLeftEdge() {
        return blankX==0;
    }

    private boolean isBlankOnRightEdge() {
        return blankX==board.getBoard()[blankY].length-1;
    }

    private boolean isBlankOnTopEdge() {
        return blankY==0;
    }

    private boolean isBlankOnBottomEdge() {
        return blankY==board.getBoard().length-1;
    }

    private static String[][] getXbyYBoard(int x, int y) {
        String[][] board = new String[y][x];
        for(int yy=0; yy!=y; yy++) {
            for(int xx=0; xx!=x; xx++) {
                board[yy][xx] = ""+(yy*y+xx+1);
            }
        }

        return board;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
}
