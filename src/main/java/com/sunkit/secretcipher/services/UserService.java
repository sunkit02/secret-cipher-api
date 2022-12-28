package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.MessageDTO;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.MessageRepository;
import com.sunkit.secretcipher.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "User with username '%s' not found", username)));
    }

    public List<MessageDTO> getSentMessages(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "User with username '%s' not found", username)));
        return messageRepository.findAllBySenderOrderByTimeSentDesc(user)
                .stream()
                .map(MessageDTO::of)
                .toList();
    }
}
