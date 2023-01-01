package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.expections.UserRegistrationException;
import com.sunkit.secretcipher.expections.UsernameAndPasswordAuthenticationException;
import com.sunkit.secretcipher.models.dtos.UserDTO;
import com.sunkit.secretcipher.models.payloads.requests.LoginRequest;
import com.sunkit.secretcipher.models.payloads.requests.RegisterNewUserRequest;
import com.sunkit.secretcipher.models.payloads.responses.LoginResponse;
import com.sunkit.secretcipher.services.AuthService;
import com.sunkit.secretcipher.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(
            @RequestBody RegisterNewUserRequest request) {
        log.info("New user attempting to register with username: {}", request.username());

        UserDTO user;
        try {
            user = UserDTO.of(userService.registerNewUser(
                    request.username(),
                    request.password(),
                    request.email()));
        } catch (UserRegistrationException e) {
            log.info("Error when registering: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {

        log.info("Authentication attempt with username: {}", request.username());

        LoginResponse response;

        try {
            response = authService.loginWithUsernameAndPassword(
                    request.username(),
                    request.password());
        } catch (UserNotFoundException | UsernameAndPasswordAuthenticationException e) {
            log.error("Failed to authenticate user with username '{}', error: {}",
                    request.username(), e.getMessage());
            return ResponseEntity
                    .status(401)
                    .body("Invalid login credentials");
        }

        return ResponseEntity.ok(response);
    }
}
