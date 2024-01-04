package com.petcare.petcare.Exceptions;

public class UserNotFoundException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public UserNotFoundException() {
        super();
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public UserNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
