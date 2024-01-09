package org.team3.board.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.team3.commons.entities.BaseMember;
import org.team3.member.entities.Member;

import java.util.UUID;

@Data
@Entity
public class BoardData extends BaseMember {

    @Id
    @GeneratedValue
    private Long seq; // 게시글 번호

    @Column(length = 50)
    private String gid = UUID.randomUUID().toString(); // 그룹 아이디

    private Board board; // 게시판 정보
    private Member member; // 유저 정보
    private String category; // 분류

    @Column(length = 30, nullable = false)
    private String poster; // 작성자

    @Column(length = 100, nullable = false)
    private String content;

    @Column(length = 100)
    private String reply; //댓글

    private int viewCnt; //조회수


}
