package com.sayit.ijmr.Exceptions;

public class DocumentAlreadyExistException extends RuntimeException {
    public DocumentAlreadyExistException(String message) {
        super(message);
    }
}
