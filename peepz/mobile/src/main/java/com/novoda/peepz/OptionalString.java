package com.novoda.peepz;

import android.support.annotation.Nullable;

public class OptionalString {
    @Nullable
    private final String value;

    public OptionalString(@Nullable String value) {
        this.value = value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public String get() {
        if (!isPresent()) {
            throw new RuntimeException("goofed");
        }
        return value;
    }
}
