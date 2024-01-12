package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.team3.member.repositories.MemberRepository;

@Component
@RequiredArgsConstructor
public class ChangePwValidator implements Validator {
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
        if(StringUtils.hasText(cntpwd)&&StringUtils.hasText(newpwd)&&StringUtils.hasText(checkpwd)){

        }


    }
}
