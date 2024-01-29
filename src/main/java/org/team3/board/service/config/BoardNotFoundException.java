package org.team3.board.service.config;

import org.springframework.http.HttpStatus;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertBackException;

public class BoardNotFoundException extends AlertBackException {
    public BoardNotFoundException() {
        super(Utils.getMessage("NotFound.board", "errors"), HttpStatus.NOT_FOUND);
    }
}
