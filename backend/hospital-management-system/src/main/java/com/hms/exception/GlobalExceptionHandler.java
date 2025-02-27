package com.hms.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {



    // 4xx errors (e.g., 400, 401, 403, 404, 405, 406, etc.)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, Object>> handleHttpClientErrorException ( HttpClientErrorException ex , WebRequest request ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return buildErrorResponse(status.getReasonPhrase() , status , request);
    }

    // 5xx errors (e.g., 500, 501, 502, 503, 504, 505, 506, 507, 508, 509, 510, 511)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, Object>> handleHttpServerErrorException ( HttpServerErrorException ex , WebRequest request ) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return buildErrorResponse(status.getReasonPhrase() , status , request);
    }


    // Helper method to build the error response
    private ResponseEntity<Map<String, Object>> buildErrorResponse ( String message , HttpStatus status , WebRequest request ) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp" , new Date());
        errorDetails.put("message" , message);
        errorDetails.put("status" , status.value());
        errorDetails.put("error" , status.getReasonPhrase());
        errorDetails.put("path" , request.getDescription(false)); // e.g., "uri=/api/..."
        return new ResponseEntity<>(errorDetails , status);
    }


    // ✅ Security - Access Denied
    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException ( Exception ex , WebRequest request ) {
        return buildErrorResponse("Access Denied: " + ex.getMessage() , HttpStatus.FORBIDDEN , request);
    }

    // ✅ DoctorAlreadyExistsException
    @ExceptionHandler(DoctorAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDoctorAlreadyExistsException ( DoctorAlreadyExistsException ex , WebRequest request ) {
        return buildErrorResponse(ex.getMessage() , HttpStatus.CONFLICT , request);
    }

    // ✅ SlotAlreadyExistsException
    @ExceptionHandler(SlotAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleSlotAlreadyExistsException ( SlotAlreadyExistsException ex , WebRequest request ) {
        return buildErrorResponse(ex.getMessage() , HttpStatus.CONFLICT , request);
    }

    @ExceptionHandler(SlotAlreadyBookedException.class)
    public ResponseEntity<Map<String, Object>> handleSlotAlreadyBookedException ( SlotAlreadyBookedException ex , WebRequest request ) {
        return buildErrorResponse(ex.getMessage() , HttpStatus.CONFLICT , request);
    }

    @ExceptionHandler(DuplicateBillException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateBillException ( DuplicateBillException ex , WebRequest request ) {
        return buildErrorResponse(ex.getMessage() , HttpStatus.CONFLICT , request);
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBillNotFoundException ( BillNotFoundException ex , WebRequest request ) {
        return buildErrorResponse(ex.getMessage() , HttpStatus.NOT_FOUND , request);
    }

    @ExceptionHandler(InvalidSlotStatusException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidSlotStatusException(InvalidSlotStatusException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFoundException(BookingNotFoundException ex, WebRequest request) {
        log.error("BookingNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(PrescriptionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePrescriptionNotFoundException(PrescriptionNotFoundException ex, WebRequest request) {
        log.error("BookingNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(PrescribedTestNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePrescribedTestNotFoundException(PrescribedTestNotFoundException ex, WebRequest request) {
        log.error("PrescribedTestNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }
    @ExceptionHandler(EMRAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEMRAlreadyExistsException(EMRAlreadyExistsException ex, WebRequest request) {
        log.error("EMRAlreadyExistsException: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

}
