package com.hms.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    // ✅ Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // ✅ Common method to build error response
    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("message", message);
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("path", request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, status);
    }

    // ✅ Security - Access Denied
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(Exception ex, WebRequest request) {
        return buildErrorResponse("Access Denied: " + ex.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    // ✅ DoctorAlreadyExistsException
    @ExceptionHandler(DoctorAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorAlreadyExistsException(DoctorAlreadyExistsException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

}
