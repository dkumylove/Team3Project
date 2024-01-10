package org.team3.member.service;

import org.team3.member.entities.Member;

public interface MemberService {

    // 로그인
    Member loginMember(Member member);

    // 회원가입
    String joinMember(Member member);

    // 회원정보 수정
    String updateMemberInfo(Member member);

    // 회원 비밀번호 수정
    String updateMemberPassword(Member member);

    // 회원탈퇴
    String deleteMember(String userId);

    // 아이디 중복체크
    String userIdCheck(Member member);

}
