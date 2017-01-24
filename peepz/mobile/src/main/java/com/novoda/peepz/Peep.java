package com.novoda.peepz;

class Peep {

    private final String id;
    private final String name;
    private final Image image;
    private final long lastSeen;

    Peep(String id, String name, Image image, long lastSeen) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.lastSeen = lastSeen;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Image image() {
        return image;
    }

    public long lastSeen() {
        return lastSeen;
    }

}
