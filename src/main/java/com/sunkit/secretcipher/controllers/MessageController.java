package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.models.message.MessageDTO;
import com.sunkit.secretcipher.models.payloads.requests.SendNewMessageRequest;
import com.sunkit.secretcipher.models.payloads.websocket.NewMessageDTO;
import com.sunkit.secretcipher.services.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/messages")
public class MessageController {

    private final SimpMessagingTemplate template;

    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(
            @RequestBody SendNewMessageRequest request) {

        log.info("Handling request: {}", request);

        MessageDTO newMessage;
        try {
            newMessage = messageService.sendNewMessage(request);
        } catch (UserNotFoundException e) {
            log.error("Error handling request: {}", request, e);
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }

        template.convertAndSend(
                "/topic/message/" + request.recipientUsername(),
                new NewMessageDTO(newMessage));

        return ResponseEntity.ok(newMessage);
    }
}
