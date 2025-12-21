package com.example.first_vigna;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class ProviderHandler extends AbstractWebSocketHandler {

    private final SessionRegistry registry;

    public ProviderHandler(SessionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        registry.provider = session;
        System.out.println("Provider connected");
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message
    ) throws Exception {

        System.out.println("Provider → Spring (TEXT)");

        if (registry.browser != null && registry.browser.isOpen()) {
            registry.browser.sendMessage(message);
        }
    }

    @Override
    protected void handleBinaryMessage(
            WebSocketSession session,
            BinaryMessage message
    ) throws Exception {

        System.out.println("Provider → Spring (BINARY)");

        if (registry.browser != null && registry.browser.isOpen()) {
            registry.browser.sendMessage(message);
        }
    }
}
