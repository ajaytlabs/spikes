package com.novoda.peepz;

public class ApiPeep {

    public String uid;
    public String name;
    public String image;
    public long timestamp;

    public ApiPeep() {
    }

    public ApiPeep(String uid, String name, String image, long timestamp) {
        this.uid = uid;
        this.name = name;
        this.image = image;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "name: " + name + ", uid: " + uid + ", image: " + image.substring(0,5) + ", timestamp: " + timestamp;
    }
}
