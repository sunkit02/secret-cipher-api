package com.sunkit.secretcipher.models.dtos;

import com.sunkit.secretcipher.models.message.Message;

public record ReceivedMessageDTO(
        Long id,
        String senderUsername,
        String recipientUsername,
        String subject,
        String message) {
    public static ReceivedMessageDTO of(Message message) {
        return new ReceivedMessageDTO(
                message.getId(),
                message.getSender().getUsername(),
                message.getRecipient().getUsername(),
                message.getSubject(),
                message.getEncodedMessage()
        );
    }
}
