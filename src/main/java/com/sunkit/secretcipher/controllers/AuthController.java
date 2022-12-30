package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.models.dtos.UserDTO;
import com.sunkit.secretcipher.models.payloads.requests.RegisterNewUserRequest;
import com.sunkit.secretcipher.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDTO registerNewUser(@RequestBody RegisterNewUserRequest request) {
        log.info("New user attempting to register with username: {}", request.username());

        return UserDTO.of(userService.registerNewUser(
                    request.username(),
                    request.password(),
                    request.email()));
    }
}
