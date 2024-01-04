package com.petcare.petcare.Exceptions;

public class UserListIsNullException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public UserListIsNullException() {
        super();
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public UserListIsNullException(String errorMessage) {
        super(errorMessage);
    }
}
