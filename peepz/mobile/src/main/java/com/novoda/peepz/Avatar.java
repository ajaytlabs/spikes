package com.novoda.peepz;

public class Avatar {

    private final String name;
    private final String id;
    private final String base64Image;

    public Avatar(String name, String id, String base64Image) {
        this.name = name;
        this.id = id;
        this.base64Image = base64Image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getBase64Image() {
        return base64Image;
    }
}
