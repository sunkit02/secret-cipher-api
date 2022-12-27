package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(
                        "User with username '%s' not found", username)));
    }
}
