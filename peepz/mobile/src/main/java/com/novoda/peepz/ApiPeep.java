package com.novoda.peepz;

public class ApiPeep {

    public String id;
    public String name;
    public String image;
    public long timestamp;

    @Override
    public String toString() {
        return "name: " + name + ", id: " + id + ", image: " + image.substring(0,5) + ", timestamp: " + timestamp;
    }
}
