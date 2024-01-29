//package org.team3.entity.board;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.apache.tomcat.jni.FileInfo;
//import org.team3.entity.abstractCommon.Base;
//import org.team3.entity.member.Member;
//
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(indexes = {
//        @Index(name="idx_bd_list", columnList = "notice DESC, createdAt DESC"),
//        @Index(name="idx_bd_category", columnList = "category")
//})
//public class BoardData extends Base {
//
//    @Id
//    @GeneratedValue
//    private Long seq;  // 게시글 번호
//
//    @Column(length=50, nullable = false) // 게시글 데이터 - 파일 떄문
//    private String gid = UUID.randomUUID().toString();  // 그룹아이디
//
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="bId")
//    private Board board;  // 게시판정보
//
//    @ManyToOne(fetch= FetchType.LAZY)
//    @JoinColumn(name="userNo")
//    private Member member;  // 유저정보
//
//    @Column(length=50)
//    private String category;  // 분류
//
//    @Column(length=30, nullable = false)
//    private String poster;  // 작성자
//
//    @Column(length=65)
//    private String guestPw; // 비회원 비밀번호
//
//    @Column(nullable = false)
//    private String subject;  // 주제
//
//    @Lob
//    @Column(nullable = false)
//    private String content;  // 내용
//
//    private boolean notice; // 공지사항 여부
//
//    private int viewCnt; // 조회수
//
//    private int commentCnt; // 댓글 수
//
//    @Transient
//    private List<CommentData> comments; // 댓글 목록
//
//    @Transient
//    private List<FileInfo> editorImages;  // 첨부이미지
//
//    @Transient
//    private List<FileInfo> attachFiles;  // 첨부파일
//}
//
