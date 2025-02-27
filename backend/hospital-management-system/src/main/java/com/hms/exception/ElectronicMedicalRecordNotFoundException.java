package com.hms.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElectronicMedicalRecordNotFoundException extends RuntimeException {
    public ElectronicMedicalRecordNotFoundException(String message) {
        super(message);
    }
}
