package com.sunkit.secretcipher.models.payloads.websocket;

import com.sunkit.secretcipher.models.message.MessageDTO;
import lombok.Getter;

@Getter
public class NewMessageDTO {
    private final PayloadType type = PayloadType.NEW_MESSAGE;
    private final MessageDTO payload;

    public NewMessageDTO(MessageDTO newMessage) {
        this.payload = newMessage;
    }
}
