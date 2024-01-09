package org.team3.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.team3.commons.Utils;

public class UnAuthorizedException extends AlertBackException {
    public UnAuthorizedException() {
        this(Utils.getMessage("UnAuthorized", "errors"));
    }

    public UnAuthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
