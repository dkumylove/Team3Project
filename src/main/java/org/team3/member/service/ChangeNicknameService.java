package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class ChangeNicknameService {
    private final MemberRepository memberRepository;
    @Transactional
    public boolean changeNickname(String userId, String newNickname) {

        Member member1 = memberRepository.findByUserId(userId).orElse(null);
        member1.setNickName(newNickname);
        memberRepository.saveAndFlush(member1);
        return true;
    }
}
