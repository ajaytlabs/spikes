package com.novoda.peepz;

import android.support.annotation.Nullable;

public class OptionalName {
    @Nullable
    private final String name;

    public OptionalName(@Nullable String name) {
        this.name = name;
    }

    public boolean isPresent() {
        return name != null;
    }

    public String get() {
        if (!isPresent()) {
            throw new RuntimeException("goofed");
        }
        return name;
    }
}
