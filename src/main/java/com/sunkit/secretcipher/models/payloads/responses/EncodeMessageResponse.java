package com.sunkit.secretcipher.models.payloads.responses;

public record EncodeMessageResponse(
        String encodedMessage,
        byte[] encodedBytes
) {
    public static EncodeMessageResponse of(String encodedMessage, byte[] encodedBytes) {
        return new EncodeMessageResponse(encodedMessage, encodedBytes);
    }
}
