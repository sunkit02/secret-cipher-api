package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.models.DecodingResult;
import com.sunkit.secretcipher.models.EncodingResult;
import com.sunkit.secretcipher.models.payloads.requests.DecodeMessageRequest;
import com.sunkit.secretcipher.models.payloads.requests.EncodeMessageRequest;
import com.sunkit.secretcipher.services.EncodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
@RequiredArgsConstructor
public class WSEncodingController {

    private final EncodingService encodingService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/encode")
    public void encodeMessage(EncodeMessageRequest request) {
        EncodingResult encodingResult = encodingService.encodeWithBytes(
                request.message(),
                request.key());

        log.info("Encode request: {}", request);
        messagingTemplate.convertAndSend(
                "/topic/updates/" + request.username(),
                encodingResult);
    }

    @MessageMapping("/private-encode")
    @SendToUser("/topic/private-encode")
    public EncodingResult getPrivateEncodingResult(
            final EncodeMessageRequest request,
            final Principal principal) {
        log.info("Encode message request: {} from user: {}", request, principal.getName());

        EncodingResult encodingResult = encodingService.encodeWithBytes(
                request.message(),
                request.key());
        log.info("Encoding message result: {}", encodingResult);
        return encodingResult;
    }

    @MessageMapping("/private-decode")
    @SendToUser("/topic/private-decode")
    public DecodingResult getPrivateDecodingResult(
            final DecodeMessageRequest request,
            final Principal principal) {
        log.info("Decode message request: {} from user: {}", request, principal.getName());

        DecodingResult decodingResult = encodingService.decodeWithBytes(
                request.message(),
                request.key());
        log.info("Decoding message result: {}", decodingResult);
        return decodingResult;
    }
}
