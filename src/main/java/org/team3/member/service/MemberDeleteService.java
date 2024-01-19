package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberDeleteService {
    private final MemberRepository memberRepository;

    public void deactivateMember(Long seq) {
        Member member = memberRepository.findById(seq).orElseThrow(() -> new RuntimeException("User not found"));
        member.setEnabled(false);
        memberRepository.save(member);
    }
}
