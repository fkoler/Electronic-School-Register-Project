package com.electric_diary.exception;

@SuppressWarnings("serial")
public class InvalidGradingTypeException extends RuntimeException {
    public InvalidGradingTypeException(String message) {
        super(message);
    }

    public InvalidGradingTypeException(Throwable cause) {
        super(cause);
    }
}