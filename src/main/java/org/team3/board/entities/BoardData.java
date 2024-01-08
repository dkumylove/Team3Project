package org.team3.board.entities;

import jakarta.persistence.*;
import org.team3.entities.Base;

import java.util.UUID;

public class BoardData extends Base {

    @Id @GeneratedValue
    private Long seq; // 게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "")
//  private Member member; // 멤버 정보, import 때문에 주석 처리

    @Column(length = 50, nullable = false)
    private String category; // 카테고리

    @Column(length = 50, nullable = false)
    private String title; // 글 제목

    @Column(length = 20, nullable = false)
    private String writer; // 작성자

    @Lob
    @Column(length = 100, nullable = false)
    private String content; // 글 내용

    private int viewCnt; // 조회수

    @Column(length = 100)
    private String reply; // 댓글
    
    private int likeCnt; // 좋아요
}
