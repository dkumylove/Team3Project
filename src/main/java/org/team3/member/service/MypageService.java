package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.team3.commons.exceptions.AlertBackException;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public boolean checkPassword(String userId, String cntpwd) {

        Member member = memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);


        // 여기서 PasswordEncoder.matches 메서드를 사용하여 비밀번호 일치 여부 확인
        return bCryptPasswordEncoder.matches(cntpwd, member.getPassword());
    }

    public void changePassword(String email, String newpwd) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.setPassword(bCryptPasswordEncoder.encode(newpwd));

        memberRepository.saveAndFlush(member);
    }
}

