package com.hms.exception;

public class DoctorAlreadyExistsException extends RuntimeException {

    public DoctorAlreadyExistsException(String message) {
        super(message);
    }
}
