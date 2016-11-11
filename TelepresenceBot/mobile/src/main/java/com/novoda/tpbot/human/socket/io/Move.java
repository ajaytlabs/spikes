package com.novoda.tpbot.human.socket.io;

public enum Move {

    LEFT("left"),
    FORWARD("forward"),
    RIGHT("right"),
    BACKWARD("backward");

    private final String rawMove;

    Move(String rawMove) {
        this.rawMove = rawMove;
    }

    public static Move from(String rawMove) {
        for (Move move : values()) {
            if (move.rawMove.equalsIgnoreCase(rawMove)) {
                return move;
            }
        }
        throw new IllegalArgumentException("No matching move for: " + rawMove);
    }
}
