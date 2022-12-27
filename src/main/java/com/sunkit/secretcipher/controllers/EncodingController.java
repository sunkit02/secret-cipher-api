package com.sunkit.secretcipher.controllers;

import com.sunkit.secretcipher.models.EncodingResult;
import com.sunkit.secretcipher.services.EncodingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin(originPatterns = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/encoding")
public class EncodingController {

    private final EncodingService encodingService;
    private final SimpMessagingTemplate template;
//    @PostMapping("/encode")
//    public ResponseEntity<?> encodeMessage(
//            @RequestBody EncodeMessageRequest request) {
//        EncodingResult encodingResult = encodingService.encode(
//                request.message(),
//                request.key(),
//                request.keytype());
//
//        return ResponseEntity.ok(encodingResult);
//    }
//
//    @PostMapping("/decode")
//    public ResponseEntity<?> decodeMessage(
//            @RequestBody DecodeMessageRequest request) {
//        DecodingResult decodingResult = encodingService.decode(
//                request.message(),
//                request.key(),
//                request.keyType());
//
//        return ResponseEntity.ok(decodingResult);
//    }

    @PostMapping("/ping")
    public ResponseEntity<?> pingUser() {
        log.info("Pinging user sunkit25");
        template.convertAndSend(
                "/topic/encoding/sunkit25",
                new EncodingResult(
                        "This is an encoded string",
                        new byte[]{1, 2, 3, 4, 5}));
        return ResponseEntity.ok().build();
    }
}
