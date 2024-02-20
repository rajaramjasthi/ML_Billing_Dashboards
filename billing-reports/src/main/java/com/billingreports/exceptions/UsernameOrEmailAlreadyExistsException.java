package com.billingreports.exceptions;

public class UsernameOrEmailAlreadyExistsException extends RuntimeException {

    public UsernameOrEmailAlreadyExistsException() {
        super("Email or Username already exists");
    }
}
