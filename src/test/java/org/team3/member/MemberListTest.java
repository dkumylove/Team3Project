package org.team3.member;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

import java.util.UUID;

@SpringBootTest
public class MemberListTest {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원목록 페이지 목록 페이징을 확인하기 위해 임의의 멤버 추가하는 테스트
     * @WithAnonymousUser -> 비회원 만들기
     * 참고하세용!@!@!@
     * 이다은 - 1월 11일
     */
    @Test //@Disabled
    @WithMockUser
    void 회원추가(){
        for(int i=100; i<200; i++){
            Member member = Member.builder().name("사용자"+i).gid(UUID.randomUUID().toString())
                    .email(i+"@gmail.com").nickName(i+"닉네임").userId(i+"아이디").password("123456").nickName("nick"+i).build();
            memberRepository.saveAndFlush(member);
        }
    }
}
