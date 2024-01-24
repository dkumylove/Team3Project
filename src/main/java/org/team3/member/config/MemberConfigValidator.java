package org.team3.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.team3.admin.member.controllers.RequestMemberConfig;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Component
@RequiredArgsConstructor
public class MemberConfigValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestMemberConfig.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RequestMemberConfig form = (RequestMemberConfig) target;
        String userId = form.getUserId();
        String mode = form.getMode();
        if(mode.equals("add") && StringUtils.hasText(userId) && memberRepository.existsByUserId(userId)) {
            errors.rejectValue("userId", "Duplicated");
        }
    }
}
