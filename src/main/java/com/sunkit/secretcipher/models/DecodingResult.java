package com.sunkit.secretcipher.models;

public record DecodingResult(String decodedString, byte[] decodedBytes) {
    public static DecodingResult of(String decodedString, byte[] decodedBytes) {
        return new DecodingResult(decodedString, decodedBytes);
    }
}
