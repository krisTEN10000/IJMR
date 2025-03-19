package com.sayit.ijmr.Exceptions;


public class DocumentNotExistException extends RuntimeException {
    public DocumentNotExistException(String message) {
        super(message);
    }
}
