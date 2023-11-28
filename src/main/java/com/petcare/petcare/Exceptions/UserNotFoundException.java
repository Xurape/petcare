package com.petcare.petcare.Exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
