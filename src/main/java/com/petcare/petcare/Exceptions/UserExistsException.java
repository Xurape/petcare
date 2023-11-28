package com.petcare.petcare.Exceptions;

public class UserExistsException extends Exception {
    public UserExistsException() {
        super();
    }

    public UserExistsException(String errorMessage) {
        super(errorMessage);
    }
}
