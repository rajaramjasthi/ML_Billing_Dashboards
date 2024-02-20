package com.billingreports.exceptions;


public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("Invalid User");
    }
}
