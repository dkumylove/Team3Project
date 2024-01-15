package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.team3.member.MemberUtil;

@Component
@RequiredArgsConstructor
public class ChangeEmailValidator implements Validator {
    private final HttpSession httpSession;
    private final MemberUtil memberUtil;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestChangeEmail.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RequestChangeEmail requestChangeEmail = (RequestChangeEmail) target;
        boolean isVerified = (boolean) httpSession.getAttribute("EmailAuthVerified");

        if(!isVerified){
            errors.rejectValue("email", "Required.verified");
        }
    }
}
