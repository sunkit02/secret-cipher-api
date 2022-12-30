package com.sunkit.secretcipher.models.dtos;

import com.sunkit.secretcipher.models.message.MessageDTO;
import com.sunkit.secretcipher.models.user.User;

import java.util.List;

public record UserDTO(
        String username,
        String email,
        List<MessageDTO> messagesSent,
        List<MessageDTO> messagesReceived
) {

    public static UserDTO of(User user) {
        List<MessageDTO> messagesSent = user.getMessagesSent()
                .stream()
                .map(MessageDTO::of)
                .toList();

        List<MessageDTO> messagesReceived = user.getMessagesReceived()
                .stream()
                .map(MessageDTO::of)
                .toList();

        return new UserDTO(
                user.getUsername(),
                user.getEmail(),
                messagesSent,
                messagesReceived
        );
    }
}
