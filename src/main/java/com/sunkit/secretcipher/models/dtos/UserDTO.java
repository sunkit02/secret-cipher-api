package com.sunkit.secretcipher.models.dtos;

import com.sunkit.secretcipher.models.message.SentMessageDTO;
import com.sunkit.secretcipher.models.user.User;

import java.util.List;

public record UserDTO(
        String username,
        String email,
        List<SentMessageDTO> messagesSent,
        List<SentMessageDTO> messagesReceived
) {

    public static UserDTO of(User user) {
        List<SentMessageDTO> messagesSent = user.getMessagesSent()
                .stream()
                .map(SentMessageDTO::of)
                .toList();

        List<SentMessageDTO> messagesReceived = user.getMessagesReceived()
                .stream()
                .map(SentMessageDTO::of)
                .toList();

        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                messagesSent,
                messagesReceived
        );
    }
}
