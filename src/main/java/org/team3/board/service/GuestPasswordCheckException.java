package org.team3.board.service;

import org.springframework.http.HttpStatus;
import org.team3.commons.exceptions.CommonException;

/**
 * 비회원 비밀번호 확인이 필요한 경우
 *
 */
public class GuestPasswordCheckException extends CommonException {
    public GuestPasswordCheckException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
