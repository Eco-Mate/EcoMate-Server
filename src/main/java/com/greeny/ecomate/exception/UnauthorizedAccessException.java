package com.greeny.ecomate.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;

public class UnauthorizedAccessException extends RuntimeException{
    public static final int statusCode = 403;
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
