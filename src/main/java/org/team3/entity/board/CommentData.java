//package org.team3.entity.board;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.team3.entity.abstractCommon.Base;
//import org.team3.entity.member.Member;
//
//@Entity
//@Data @Builder
//@NoArgsConstructor @AllArgsConstructor
//public class CommentData extends Base {
//    @Id @GeneratedValue
//    private Long seq;  // 코멘트 넘버
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="boardData_seq")
//    private BoardData boardData;  // 게시물정보
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="userNo")
//    private Member member;  // 유저정보
//
//    @Column(length=40, nullable = false)
//    private String poster;  // 작성자
//
//    @Column(length=65)
//    private String guestPw;  // 비회원 비밀번호
//
//    @Lob
//    private String content;  // 내용
//}
