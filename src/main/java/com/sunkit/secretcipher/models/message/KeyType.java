package com.sunkit.secretcipher.models.message;

public enum KeyType {
    NUM_ARRAY("NUM_ARRAY"),
    STRING("STRING");

    private final String name;

    KeyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
