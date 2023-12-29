package org.team3.commons.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CommonException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
