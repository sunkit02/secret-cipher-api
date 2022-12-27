package com.sunkit.secretcipher.models.payloads.requests;

public record DecodeMessageRequest(
        String username,
        String key,
        String message
) {
}
