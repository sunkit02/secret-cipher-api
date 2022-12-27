package com.sunkit.secretcipher.models.payloads.responses;

public record DecodeMessageResponse(
        String decodedMessage,
        byte[] decodedBytes
) {
    public static DecodeMessageResponse of(String decodedMessage, byte[] decodedBytes) {
        return new DecodeMessageResponse(decodedMessage, decodedBytes);
    }
}
