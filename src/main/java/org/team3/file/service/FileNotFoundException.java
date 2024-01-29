package org.team3.file.service;

import org.team3.commons.Utils;
import org.team3.commons.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class FileNotFoundException extends CommonException {
    public FileNotFoundException() {
        super(Utils.getMessage("NotFound.file", "errors"), HttpStatus.NOT_FOUND);
    }
}