package com.novoda.peepz;

public class ApiPeep {

    public String uid;
    public String name;
    public long lastSeen;

    public ApiPeep() {
    }

    public ApiPeep(String uid, String name, long lastSeen) {
        this.uid = uid;
        this.name = name;
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return "{ name: " + name + ", uid: " + uid + ", lastSeen: " + lastSeen + "}";
    }

}
