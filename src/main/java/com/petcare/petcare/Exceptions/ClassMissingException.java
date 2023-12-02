package com.petcare.petcare.Exceptions;

public class ClassMissingException extends Exception {
    public ClassMissingException() { 
        super(); 
    }

    public ClassMissingException(String message) {
        super(message);
    }
}
