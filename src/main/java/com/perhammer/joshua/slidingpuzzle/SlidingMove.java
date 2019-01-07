package com.perhammer.joshua.slidingpuzzle;

public enum SlidingMove {
    NORTH, SOUTH, EAST, WEST;

    public static SlidingMove getOpposite(SlidingMove move) {
        switch (move) {
            case NORTH:
                return SOUTH;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                throw new RuntimeException("Invalid move, can't get opposite of "+move);
        }
    }

    public SlidingMove getOpposite() {
        return getOpposite(this);
    }
}
