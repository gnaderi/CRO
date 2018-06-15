package com.gnaderi.interview.cro;

public class CROException extends RuntimeException {

    public CROException(String message) {
        super(message);
    }

    public CROException(String message, Throwable cause) {
        super(message, cause);
    }
}