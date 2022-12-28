package com.sunkit.secretcipher.models.message;

import java.sql.Timestamp;

public record MessageDTO(
        Long id,
        String senderUsername,
        String recipientUsername,
        String key,
        String subject,
        String message,
        Timestamp timeSent
) {
    public static MessageDTO of(Message message) {
        return new MessageDTO(
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
