package com.sunkit.secretcipher.payloads.requests;

import com.sunkit.secretcipher.models.message.KeyType;

public record SendNewMessageRequest(
        String senderUsername,
        String recipientUsername,
        KeyType keyType,
        String key,
        String message
) {}
