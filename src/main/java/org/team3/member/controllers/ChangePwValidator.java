package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.team3.member.repositories.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.team3.commons.validators.PasswordValidator;
import org.team3.member.repositories.MemberRepository;

@Component
@RequiredArgsConstructor
public class ChangePwValidator implements Validator, PasswordValidator {
    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestChangePw.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestChangePw form = (RequestChangePw) target;
        String cntpwd = form.getCntpwd();
        String newpwd = form.getNewpwd();
        String checkpwd = form.getCheckpwd();

        // 2. 비밀번호 복잡성 체크 : 대소문자 각각 1개이상, 숫자 1개이상, 특문 1개이상 포함
        if(StringUtils.hasText(newpwd)
                && (!alphaCheck(newpwd, true)
                || !numberCheck(newpwd)
                || !specialcharsCheck(newpwd))) {
            errors.rejectValue("password", "Complexity");
        }

        // 3. 비밀번호, 비밀번호 확인 일치여부 체크
        if(StringUtils.hasText(newpwd) && StringUtils.hasText(checkpwd) && !newpwd.equals(checkpwd)) {
            errors.rejectValue("confirmPassword", "Mismatch.password");
        }

        }
    }

