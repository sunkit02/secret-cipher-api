package com.sunkit.secretcipher.models.payloads.requests;

public record EncodeMessageRequest(
        String username,
        String key,
        String message) {
}
