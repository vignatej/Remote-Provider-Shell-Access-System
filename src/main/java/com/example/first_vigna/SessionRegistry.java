package com.example.first_vigna;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SessionRegistry {
    public volatile WebSocketSession browser;
    public volatile WebSocketSession provider;
}
