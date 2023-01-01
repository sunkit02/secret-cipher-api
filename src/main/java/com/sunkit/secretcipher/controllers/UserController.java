package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.dtos.ReceivedMessageDTO;
import com.sunkit.secretcipher.models.message.SentMessageDTO;
import com.sunkit.secretcipher.models.payloads.requests.GetReceivedMessagesRequest;
import com.sunkit.secretcipher.models.payloads.requests.GetSentMessagesRequest;
import com.sunkit.secretcipher.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/sent-messages")
    public ResponseEntity<?> getSentMessages(
            @RequestBody GetSentMessagesRequest request) {
        log.info("User '{}' is fetching all sent messages", request.username());
        List<SentMessageDTO> sentMessages;
        try {
            sentMessages = userService.getSentMessages(request.username());

        } catch (UserNotFoundException e) {
            log.error("Error fetching sent messages for user '{}': {}",
                    request.username(), e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(sentMessages);
    }

    @PostMapping("/received-messages")
    public ResponseEntity<?> getReceivedMessages(
            @RequestBody GetReceivedMessagesRequest request) {
        log.info("User '{}' is fetching all received messages", request.username());
        List<ReceivedMessageDTO> receivedMessages;
        try {
            receivedMessages = userService.getReceivedMessages(request.username());
        } catch (UserNotFoundException e) {
            log.error("Error fetching received messages for user '{}': {}",
                    request.username(), e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
        return ResponseEntity.ok(receivedMessages);
    }

}

