package org.team3.board.service;

import org.springframework.http.HttpStatus;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertBackException;

public class BoardDataNotFoundException extends AlertBackException {
    public BoardDataNotFoundException() {
        super(Utils.getMessage("NotFound.boardData", "errors"), HttpStatus.NOT_FOUND);
    }
}
