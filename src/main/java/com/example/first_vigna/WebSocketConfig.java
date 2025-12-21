package com.example.first_vigna;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final TerminalHandler terminalHandler;
    private final ProviderHandler providerHandler;

    public WebSocketConfig(
            TerminalHandler terminalHandler,
            ProviderHandler providerHandler
    ) {
        this.terminalHandler = terminalHandler;
        this.providerHandler = providerHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(terminalHandler, "/terminal").setAllowedOrigins("*");
        registry.addHandler(providerHandler, "/provider").setAllowedOrigins("*");
    }
}
