package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.team3.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class FindIdValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestFindId.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // 이메일 + 회원명 조합으로 조회 되는지 체크
        RequestFindId form = (RequestFindId) target;
        String email = form.email();
        String name = form.name();

        if ((StringUtils.hasText(email) && !memberRepository.existsByEmail(email)) ||
                (StringUtils.hasText(name) && !memberRepository.existsByName(name))) {
            errors.reject("NotFound.member");
        }
    }
}

