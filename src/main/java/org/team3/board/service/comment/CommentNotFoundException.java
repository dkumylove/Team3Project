package org.team3.board.service.comment;

import org.springframework.http.HttpStatus;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertBackException;

public class CommentNotFoundException extends AlertBackException {
    public CommentNotFoundException() {
        super(Utils.getMessage("NotFound.comment", "errors"), HttpStatus.NOT_FOUND);
    }
}
