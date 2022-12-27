package com.sunkit.secretcipher.models;

public record EncodingResult (String encodedString, byte[] encodedBytes) {
    public static EncodingResult of(String encodedString, byte[] encodedBytes) {
        return new EncodingResult(encodedString, encodedBytes);
    }
}
