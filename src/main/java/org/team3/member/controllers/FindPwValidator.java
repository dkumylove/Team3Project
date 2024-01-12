package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.team3.member.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 비밀번호 찾기 추가 검증 처리
 *
 */
@Component
@RequiredArgsConstructor
public class FindPwValidator implements Validator {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestFindPw.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        // 아이디 + 이메일 조합으로 조회 되는지 체크
        RequestFindPw form = (RequestFindPw) target;
        String email = form.email();
        String userId = form.userId();

        if (StringUtils.hasText(email) && StringUtils.hasText(userId) && !memberRepository.existsByEmailAndName(email, userId)) {
            errors.reject("NotFound.member");
        }

        boolean isVerified = (boolean) httpSession.getAttribute("EmailAuthVerified");
        if(!isVerified){
            errors.rejectValue("email", "Required.verified");
        }
    }
}