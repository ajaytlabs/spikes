package com.novoda.peepz;

public class ApiPeep {

    public String uid;
    public String name;
    public String image;
    public long timestamp;

    @Override
    public String toString() {
        return "name: " + name + ", uid: " + uid + ", image: " + image.substring(0,5) + ", timestamp: " + timestamp;
    }
}
