package org.team3.member.service;

import org.springframework.http.HttpStatus;
import org.team3.commons.exceptions.CommonException;

public class MemberNotFoundException extends CommonException {
    public MemberNotFoundException() {
        super("등록된 회원이 아입니다.", HttpStatus.NOT_FOUND);
    }
}