package com.novoda.peepz;

public class OptionalTimestamp {
    private final long timestamp;

    public OptionalTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isPresent() {
        return timestamp != 0;
    }

    public long get() {
        if (!isPresent()) {
            throw new RuntimeException("goofed");
        }
        return timestamp;
    }
}
