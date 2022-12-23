package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.Message;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with username: " + username + " not found"));
    }

    /**
     * Links the newly created message to the sender and recipient
     * @param sender
     * @param recipient
     * @param message
     */
    public void sendNewMessage(User sender, User recipient, Message message) {
        sender.getMessagesSent().add(message);
        recipient.getMessagesReceived().add(message);
        userRepository.saveAll(List.of(sender, recipient));
    }
}
