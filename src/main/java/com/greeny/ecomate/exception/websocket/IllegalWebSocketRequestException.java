package com.greeny.ecomate.exception.websocket;

public class IllegalWebSocketRequestException extends RuntimeException {
    public static final int statusCode = 400;
    public IllegalWebSocketRequestException(String message) {
        super(message);
    }
}
