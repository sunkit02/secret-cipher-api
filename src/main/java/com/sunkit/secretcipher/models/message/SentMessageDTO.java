package com.sunkit.secretcipher.models.message;

import java.sql.Timestamp;

public record SentMessageDTO(
        Long id,
        String senderUsername,
        String recipientUsername,
        String key,
        String subject,
        String message,
        Timestamp timeSent
) {
    public static SentMessageDTO of(Message message) {
        return new SentMessageDTO(
                message.getId(),
                message.getSender().getUsername(),
                message.getRecipient().getUsername(),
                message.getEncodingKey(),
                message.getSubject(),
                message.getMessage(),
                message.getTimeSent()
        );
    }
}
