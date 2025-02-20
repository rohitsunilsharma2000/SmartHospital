package com.hms.exception;

// DoctorAlreadyExistsException.java
public class DoctorAlreadyExistsException extends RuntimeException {
    public DoctorAlreadyExistsException(String message) {
        super(message);
    }
}
