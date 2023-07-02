package com.example.demotaskregistration.exception;

public enum ErrorCodes {

    USER_NOT_FOUND(1001),
    EMAIL_ALREADY_EXIST(1002), USER_NOT_VALID(1003), NOT_AUTHORIZED(1004);
    private int code;
    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
