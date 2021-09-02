package com.evil.inc.taskman.service.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String s) {
        super(s);
    }
}
