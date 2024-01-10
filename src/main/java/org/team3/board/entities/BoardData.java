package org.team3.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.team3.commons.entities.BaseMember;
import org.team3.file.entities.FileInfo;
import org.team3.member.entities.Member;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class BoardData extends BaseMember {
    /**
     * BoardData entity 작업
     * 이기흥 - 1월 9일
     * 이지은 - 1월 9일
     */
    @Id
    @GeneratedValue
    private Long seq; // 게시글 번호

    @Column(length = 50) // 게시글 데이터 - 파일 떄문
    private String gid = UUID.randomUUID().toString(); // 그룹 아이디

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="bId")
    private Board board; // 게시판 정보

    @Column(length=50)
    private String category; // 분류

    @Column(length = 30, nullable = false)
    private String poster; // 작성자

    @Column(length = 100, nullable = false)
    private String content;


    @Column(length = 100)
    private String reply; //답글

    private int viewCnt; //조회수

    private int commentCnt; // 댓글 수

    @ManyToOne
    private Member member;  // 유저정보

    /* 연관관계 필요합니다 */
    // @OneToMany
    @Transient
    private List<CommentData> comments; // 댓글 목록 -> 댓글

    @Transient
    private List<FileInfo> editorImages;  // 첨부이미지

    @Transient
    private List<FileInfo> attachFiles;  // 첨부파일

    /* 나(게시글)를 찜한 멤버 - 보류
    * 이다은 - 1월 9일
    * */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> memberList=new ArrayList<>();

}
