package com.hms.exception;

// SlotReservationAlreadyExistsException.java
public class SlotReservationAlreadyExistsException extends RuntimeException {
    public SlotReservationAlreadyExistsException(String message) {
        super(message);
    }
}
