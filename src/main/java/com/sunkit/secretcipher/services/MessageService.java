package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.Message;
import com.sunkit.secretcipher.models.message.SentMessageDTO;
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
    private final EncodingService encodingService;

    public SentMessageDTO sendNewMessage(SendNewMessageRequest request) throws UserNotFoundException {
        return sendNewMessage(
                request.senderUsername(),
                request.recipientUsername(),
                request.key(),
                request.subject(),
                request.message());
    }

    public SentMessageDTO sendNewMessage(
            String senderUsername,
            String recipientUsername,
            String key,
            String subject,
            String messagePayload) throws UserNotFoundException {

        User sender = userService.findByUsername(senderUsername);
        User recipient = userService.findByUsername(recipientUsername);

        String encodedMessage = encodingService.encodeWithBytes(messagePayload, key).encodedString();

        Message message = Message.builder()
                .sender(sender)
                .recipient(recipient)
                .encodingKey(key)
                .subject(subject.isBlank() ? "Subject: blank" : subject.trim())
                .message(messagePayload)
                .encodedMessage(encodedMessage)
                .timeSent(new Timestamp(System.currentTimeMillis()))
                .build();

        messageRepository.save(message);

        return SentMessageDTO.of(message);
    }
}
