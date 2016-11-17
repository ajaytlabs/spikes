package com.novoda.tpbot.human.controller;

public enum Direction {
    FORWARD("forward"),
    BACKWARD("backward"),
    STEER_RIGHT("steer_right"),
    STEER_LEFT("steer_left");

    private final String rawDirection;

    Direction(String rawDirection) {
        this.rawDirection = rawDirection;
    }

    public static Direction from(String rawDirection) {
        for (Direction direction : values()) {
            if (direction.rawDirection.equalsIgnoreCase(rawDirection)) {
                return direction;
            }
        }
        throw new IllegalArgumentException("No matching direction for: " + rawDirection);
    }

}
