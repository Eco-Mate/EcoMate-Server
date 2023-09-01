package com.greeny.ecomate.exception.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;

public class FirebaseMessageException extends RuntimeException{
    public FirebaseMessageException(String message) {
        super(message);
    }
}
