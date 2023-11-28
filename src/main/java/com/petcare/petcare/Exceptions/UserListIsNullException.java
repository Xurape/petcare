package com.petcare.petcare.Exceptions;

public class UserListIsNullException extends Exception {
    public UserListIsNullException() {
        super();
    }

    public UserListIsNullException(String errorMessage) {
        super(errorMessage);
    }
}
