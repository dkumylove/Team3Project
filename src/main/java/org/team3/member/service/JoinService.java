package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.team3.member.controllers.JoinValidator;
import org.team3.member.controllers.RequestJoin;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    public void process(RequestJoin form, Errors errors) {

        validator.validate(form, errors);

        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

//        Member member = new Member();
//        member.setEmail(form.getEmail());
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setUserId(form.getUserId());
        member.setName(form.getName());
        member.setPassword(hash);

        process(member);
    }

    /**
     * DB에 저장
     * @param member
     */
    public void process(Member member) {
        memberRepository.saveAndFlush(member);
    }
}
