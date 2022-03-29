package com.foodtech.timetracking.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    private final ExceptionDetails exceptionDetails;

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleValidationException(EntityNotFoundException ex) {
        log.error("Not Found Exception: ", ex);
        return new ResponseEntity<>(exceptionDetails.from(ex, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CheckInException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleCheckInException(CheckInException ex) {
        log.error("CheckIn exception: ", ex);
        return new ResponseEntity<>(exceptionDetails.from(ex, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckOutException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleCheckOutException(CheckOutException ex) {
        log.error("CheckOut exception: ", ex);
        return new ResponseEntity<>(exceptionDetails.from(ex, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
