package com.example.Location.Intelligence.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("The connection established");

        session.sendMessage(new TextMessage("Connection established"));

    }
    public void handleTextManager(WebSocketSession session,)
}
