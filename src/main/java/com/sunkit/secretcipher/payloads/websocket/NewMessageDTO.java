package com.sunkit.secretcipher.payloads.websocket;

import com.sunkit.secretcipher.models.message.MessageDTO;
import lombok.Getter;

import static com.sunkit.secretcipher.payloads.websocket.PayloadType.NEW_MESSAGE;

@Getter
public class NewMessageDTO {
    private final PayloadType type = NEW_MESSAGE;
    private final MessageDTO payload;

    public NewMessageDTO(MessageDTO newMessage) {
        this.payload = newMessage;
    }
}
