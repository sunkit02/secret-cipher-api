package com.sunkit.secretcipher.models.payloads.websocket;

import com.sunkit.secretcipher.models.message.SentMessageDTO;
import lombok.Getter;

@Getter
public class NewMessageDTO {
    private final PayloadType type = PayloadType.NEW_MESSAGE;
    private final SentMessageDTO payload;

    public NewMessageDTO(SentMessageDTO newMessage) {
        this.payload = newMessage;
    }
}
