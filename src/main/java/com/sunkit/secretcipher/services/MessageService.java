package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.Message;
import com.sunkit.secretcipher.models.message.MessageDTO;
import com.sunkit.secretcipher.models.payloads.requests.SendNewMessageRequest;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageDTO sendNewMessage(SendNewMessageRequest request) throws UserNotFoundException {
        return sendNewMessage(
                request.senderUsername(),
                request.recipientUsername(),
                request.key(),
                request.subject(),
                request.message());
    }

    public MessageDTO sendNewMessage(
            String senderUsername,
            String recipientUsername,
            String key,
            String subject,
            String messagePayload) throws UserNotFoundException {

        User sender = userService.findByUsername(senderUsername);
        User recipient = userService.findByUsername(recipientUsername);

        Message message = Message.builder()
                .sender(sender)
                .recipient(recipient)
                .encodingKey(key)
                .subject(subject.isBlank() ? "Subject: blank" : subject.trim())
                .message(messagePayload)
                .timeSent(new Timestamp(System.currentTimeMillis()))
                .build();

        messageRepository.save(message);

        return MessageDTO.of(message);
    }
}
