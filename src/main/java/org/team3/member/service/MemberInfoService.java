package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

/**
 * 조회
 */
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username)  // 이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username)  // 아이디 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username)));  // 없는경우 예외처리

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .build();
    }
}
