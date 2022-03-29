package com.foodtech.timetracking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CheckInException extends RuntimeException {

    public CheckInException(final String message) {
        super(message);
    }
}
