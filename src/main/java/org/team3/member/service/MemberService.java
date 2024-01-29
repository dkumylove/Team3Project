package org.team3.member.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.team3.member.entities.Member;

public interface MemberService extends JpaRepository<Member, String> {

    // 로그인
//    Member loginMember(Member member);

    // 회원가입
//    String joinMember(Member member);

    // 회원 닉네임 수정
    // public void updateMemberNickname(String updateMemberNickname);

    // 회원 비밀번호 수정
    String updateMemberPassword(String updateMemberPassword);

    // 회원탈퇴
    public String deleteMember(String deleteMember);

    // 아이디 중복체크
//    String userIdCheck(Member member);

    // 이메일 수정
    public void updateMemberMail(String updateMemberMail);
}
