package com.novoda.peepz;

public class ApiImage {

    public String payload;
    public long timestamp;

    public ApiImage() {
    }

    public ApiImage(String payload, long timestamp) {
        this.payload = payload;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{ payload: " + payload.substring(0, 5) + ", timestamp: " + timestamp + "}";
    }

}
