package com.petcare.petcare.Exceptions;

public class UserExistsException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public UserExistsException() {
        super();
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public UserExistsException(String errorMessage) {
        super(errorMessage);
    }
}
