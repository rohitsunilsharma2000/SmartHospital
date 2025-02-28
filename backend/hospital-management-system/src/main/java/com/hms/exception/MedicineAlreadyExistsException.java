package com.hms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MedicineAlreadyExistsException extends RuntimeException {
    public MedicineAlreadyExistsException ( String message) {
        super(message);
    }
}
