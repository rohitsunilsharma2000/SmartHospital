package com.hms.exception;

public class EMRAlreadyExistsException extends RuntimeException {

    public EMRAlreadyExistsException ( String message) {
        super(message);
    }
}
