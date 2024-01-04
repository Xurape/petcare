package com.petcare.petcare.Exceptions;

public class CouldNotSerializeException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public CouldNotSerializeException() {
        super();
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public CouldNotSerializeException(String errorMessage) {
        super(errorMessage);
    }
}
