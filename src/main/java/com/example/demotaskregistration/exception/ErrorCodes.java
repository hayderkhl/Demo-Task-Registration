package com.example.demotaskregistration.exception;

public enum ErrorCodes {

    USER_NOT_FOUND(1001),
    EMAIL_ALREADY_EXIST(1002),
    USER_NOT_VALID(1003),
    NOT_AUTHORIZED(1004),
    BorrowedBook_Not_Found(1005),
    BORROWED_BOOK_NOT_VALID(1006),
    Book_And_User_Not_Found(1007),
    BOOK_NOT_VALID(1008),
    Book_Not_Found(1009),
    THIS_BOOK_ALREADY_IN_USE(1010);
    private int code;
    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
