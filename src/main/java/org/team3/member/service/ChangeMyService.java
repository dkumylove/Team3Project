package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.team3.member.controllers.ChangeEmailValidator;
import org.team3.member.controllers.RequestChangeEmail;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class ChangeMyService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 현재 비밀번호가 일치하는 지 여부
     * @param userId : 현재 로그인한 사용자 email
     * @param cntpwd : 현재 입력한 비밀번호
     * @return
     */
    public boolean checkPassword(String userId, String cntpwd) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new);

        // 여기서 PasswordEncoder.matches 메서드를 사용하여 비밀번호 일치 여부 확인
        return bCryptPasswordEncoder.matches(cntpwd, member.getPassword());
    }

    /**
     * 새 비밀번호 저장...
     * @param userId : 현재 로그인 한 사용자
     * @param newpwd : 새 비밀번호
     */
    // 이메일을 통해서 멤버값을 가져오고 새로운 비밀번호로 등록...
    public boolean changePassword(String userId, String newpwd) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(MemberNotFoundException::new);

        member.setPassword(bCryptPasswordEncoder.encode(newpwd));
        memberRepository.saveAndFlush(member);
        return true;
    }

    @Transactional
    public boolean changeNickname(String userId, String newNickname) {

        Member member1 = memberRepository.findByUserId(userId).orElse(null);
        member1.setNickName(newNickname);
        memberRepository.saveAndFlush(member1);
        return true;
    }


    private final ChangeEmailValidator validator;

    @Transactional
    public void changeEmail(String email, String newemail) {
        Member member1 = memberRepository.findByEmail(email).orElse(null);
        member1.setEmail(newemail);
        memberRepository.saveAndFlush(member1);
    }

    public void process(RequestChangeEmail form, Errors errors) {
        validator.validate(form, errors);
        if (errors.hasErrors()) {
            return;
        }
    }

}
