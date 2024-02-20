package com.billingreports.exceptions;

public class UsernameOrPasswordIncorrectException extends RuntimeException {

    public UsernameOrPasswordIncorrectException() {
        super("Incorrect username or password");
    }
}
