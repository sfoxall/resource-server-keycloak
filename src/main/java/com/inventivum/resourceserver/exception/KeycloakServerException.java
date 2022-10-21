package com.inventivum.resourceserver.exception;

public class KeycloakServerException extends RuntimeException {

    public KeycloakServerException() {
    }

    public KeycloakServerException(String message){
        super(message);
    }

    public KeycloakServerException(String message, Throwable cause){
        super(message, cause);
    }

    public KeycloakServerException(Throwable cause){
        super(cause);
    }

    public KeycloakServerException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writeableStackTrace){
        super(message, cause, enableSuppression, writeableStackTrace);
    }
}
