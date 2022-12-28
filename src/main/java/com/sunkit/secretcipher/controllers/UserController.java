package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.MessageDTO;
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
        List<MessageDTO> sentMessages;
        try {
            sentMessages = userService.getSentMessages(request.username());

        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        return ResponseEntity.ok(sentMessages);
    }

}

