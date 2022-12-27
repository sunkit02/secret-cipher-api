package com.sunkit.secretcipher.models.message;

import java.sql.Timestamp;

public record MessageDTO(
        String senderUsername,
        String recipientUsername,
        String key,
        String message,
        Timestamp timeSent
) {
    public static MessageDTO of(Message message) {
        return new MessageDTO(
                message.getSender().getUsername(),
                message.getRecipient().getUsername(),
                message.getEncodingKey(),
                message.getMessage(),
                message.getTimeSent()
        );
    }
}
