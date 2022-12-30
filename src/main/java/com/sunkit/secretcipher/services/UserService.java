package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.CustomConstraintViolation;
import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.MessageDTO;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.MessageRepository;
import com.sunkit.secretcipher.repos.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                                String email) {
        
        List<String> errorMessages = new ArrayList<>();

        if (userRepository.existsByUsername(username)) {
            errorMessages.add("Username '" + username + "' already taken");
        }

        if (email != null && userRepository.existsByEmail(email)) {
            errorMessages.add("Email '" + email + "' already taken");
        }

        if (password == null || password.isBlank()) {
            errorMessages.add("Password cannot be empty");
        } else if (password.length() < 8 || password.length() > 50) {
            errorMessages.add("Password must be between 8 and 50 characters");
        }

        throwConstraintViolationExceptionsIfAny(errorMessages);

        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        return userRepository.save(newUser);
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

    private void throwConstraintViolationExceptionsIfAny(List<String> errorMessages) {
        if (errorMessages.size() > 0) {
            throw new ConstraintViolationException(
                    errorMessages
                            .stream()
                            .map(CustomConstraintViolation::new)
                            .collect(Collectors.toSet())
            );
        }
    }
}
