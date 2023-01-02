package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.expections.UserRegistrationException;
import com.sunkit.secretcipher.models.dtos.ReceivedMessageDTO;
import com.sunkit.secretcipher.models.message.SentMessageDTO;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.MessageRepository;
import com.sunkit.secretcipher.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "User with username '%s' not found", username)));
    }

    public User registerNewUser(String username,
                                String password,
                                String email) throws UserRegistrationException {

        List<String> errorMessages = new ArrayList<>();

        if (userRepository.existsByUsername(username)) {
            errorMessages.add("Username '" + username + "' already taken");
        }

        if (email != null && (!email.isBlank() && userRepository.existsByEmail(email))) {
            errorMessages.add("Email '" + email + "' already taken");
        }

        if (password == null || password.isBlank()) {
            errorMessages.add("Password cannot be empty");
        } else if (password.length() < 8 || password.length() > 50) {
            errorMessages.add("Password must be between 8 and 50 characters");
        }

        if (!errorMessages.isEmpty()) {
            throw new UserRegistrationException(compileErrorMessages(errorMessages));
        }

        if (email != null && email.isBlank()) email = null;

        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        return userRepository.save(newUser);
    }

    public List<SentMessageDTO> getSentMessages(String username) throws UserNotFoundException {
        User user = findByUsername(username);
        return messageRepository.findMessagesBySenderOrderByTimeSentDesc(user)
                .stream()
                .map(SentMessageDTO::of)
                .toList();
    }

    public List<ReceivedMessageDTO> getReceivedMessages(String username) throws UserNotFoundException {
        User user = findByUsername(username);
        return messageRepository.findMessagesByRecipientOrderByTimeSentDesc(user)
                .stream()
                .map(ReceivedMessageDTO::of)
                .toList();
    }


    private String compileErrorMessages(List<String> errorMessages) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        for (int i = 0; i < errorMessages.size(); i++) {
            errorMessageBuilder.append(
                    String.format("%s. %s\n", i + 1, errorMessages.get(i)));
        }
        return errorMessageBuilder.toString();
    }
}
