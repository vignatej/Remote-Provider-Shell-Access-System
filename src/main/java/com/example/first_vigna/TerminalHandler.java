package com.example.first_vigna;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class TerminalHandler extends AbstractWebSocketHandler {

    private final SessionRegistry registry;

    public TerminalHandler(SessionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        registry.browser = session;
        System.out.println("Browser connected");
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {

        System.out.println("Browser → Spring (TEXT): " + message.getPayload());

        if (registry.provider != null && registry.provider.isOpen()) {
            registry.provider.sendMessage(message);
        }
    }

    @Override
    protected void handleBinaryMessage(
            WebSocketSession session,
            BinaryMessage message
    ) throws Exception {

        System.out.println(
            "Browser → Spring (BINARY): " + message.getPayloadLength()
        );

        if (registry.provider != null && registry.provider.isOpen()) {
            registry.provider.sendMessage(message);
        }
    }
}
