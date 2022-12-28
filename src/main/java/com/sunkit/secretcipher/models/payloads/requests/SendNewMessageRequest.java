package com.sunkit.secretcipher.models.payloads.requests;

public record SendNewMessageRequest(
        String senderUsername,
        String recipientUsername,
        String key,
        String subject,
        String message
) {}
