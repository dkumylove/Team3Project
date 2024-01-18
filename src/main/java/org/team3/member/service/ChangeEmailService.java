package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.team3.file.service.FileUploadService;
import org.team3.member.Authority;
import org.team3.member.controllers.ChangeEmailValidator;
import org.team3.member.controllers.JoinValidator;
import org.team3.member.controllers.RequestChangeEmail;
import org.team3.member.controllers.RequestJoin;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.repositories.AuthoritiesRepository;
import org.team3.member.repositories.MemberRepository;

import java.util.Optional;


/**
 * 이메일 수정 서비스
 * 이지은
 * 1월 12일
 *
 * 수정 1월 15일 - 이다은
 */
@Service
@RequiredArgsConstructor
public class ChangeEmailService {

    private final MemberRepository memberRepository;
    private final ChangeEmailValidator validator;

    public void changeEmail(Member member, String email) {

        Member member1 = memberRepository.findById(member.getSeq()).orElse(null);
        member1.setEmail(email);
        memberRepository.saveAndFlush(member1);
    }

    public void process(RequestChangeEmail form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

    }
}



