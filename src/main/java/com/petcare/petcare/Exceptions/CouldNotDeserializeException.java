package com.petcare.petcare.Exceptions;

public class CouldNotDeserializeException extends Exception {
    /**
     *
     * Constructor
     *
     */
    public CouldNotDeserializeException() {
        super();
    }

    /**
     *
     * Constructor
     *
     * @param errorMessage Error message
     *
     */
    public CouldNotDeserializeException(String errorMessage) {
        super(errorMessage);
    }
}
