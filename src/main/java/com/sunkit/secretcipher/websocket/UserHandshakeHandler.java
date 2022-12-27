package com.sunkit.secretcipher.websocket;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UserHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(
            @NotNull ServerHttpRequest request,
            @NotNull WebSocketHandler wsHandler,
            @NotNull Map<String, Object> attributes) {
        String randomId = UUID.randomUUID().toString();

        String username = (String) attributes.get("username");

        log.info("User '{}' connected with id: {}", username, randomId);

        return new UserPrincipal(randomId);
    }
}
