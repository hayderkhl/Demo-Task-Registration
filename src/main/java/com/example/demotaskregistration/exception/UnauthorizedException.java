package com.example.demotaskregistration.exception;

import lombok.Getter;

public class UnauthorizedException extends RuntimeException{

    @Getter
    private ErrorCodes errorCode;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UnauthorizedException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
