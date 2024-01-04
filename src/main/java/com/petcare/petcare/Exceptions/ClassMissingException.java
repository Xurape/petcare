package com.petcare.petcare.Exceptions;

public class ClassMissingException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public ClassMissingException() { 
        super(); 
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public ClassMissingException(String errorMessage) {
        super(errorMessage);
    }
}
