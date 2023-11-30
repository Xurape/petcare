package com.petcare.petcare.Exceptions;

public class CouldNotDeserializeException extends Exception {
    public CouldNotDeserializeException() {
        super();
    }

    public CouldNotDeserializeException(String message) {
        super(message);
    }
}
