package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.team3.commons.Utils;
import org.team3.email.service.EmailSendService;
import org.team3.member.controllers.FindIdValidator;
import org.team3.member.controllers.RequestFindId;
import org.team3.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class FindIdService {
    private final FindIdValidator validator;
    private final MemberRepository repository;
    private final EmailSendService sendService;
    private final PasswordEncoder encoder;
    private final Utils utils;

    public void process(RequestFindId form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) { // 유효성 검사 실패시에는 처리 중단
            return;
        }

        // 비밀번호 초기화
        // showId(form.email());
    }

    public void reset(String email) {

    }
}
