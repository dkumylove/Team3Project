package org.team3.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.team3.commons.entities.Base;
import org.team3.file.entities.FileInfo;
import org.team3.member.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@Table(indexes = {
        @Index(name="idx_boardData_basic", columnList = "notice DESC, createdAt DESC")
})
public class BoardData extends Base {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bid")
    private Board board;

    @Column(length=50)
    private String category; // 분류

    @Column(length = 40, nullable = false)
    private String poster; // 작성자

    private String guestPw; // 비회원 비밀번호

    private boolean notice; // 공지글 여부 - true : 공지글

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 20)
    private String ip; // IP 주소

    private String ua; // User-Agent : 브라우저 정보

    private int num1; // 추가 필드 : 정수
    private int num2; // 추가 필드 : 정수
    private int num3; // 추가 필드 : 정수

    @Column(length = 100)
    private String text1; // 추가 필드 : 한줄 텍스트

    @Column(length = 100)
    private String text2; // 추가 필드 : 한줄 텍스트

    @Column(length = 100)
    private String text3; // 추가 필드 : 한줄 텍스트

    @Lob
    private String longText1; // 추가 필드 : 여러줄 텍스트

    @Lob
    private String longText2; // 추가 필드 : 여러줄 텍스트

    @Lob
    private String longText3; // 추가 필드 : 여러줄 텍스트

    @Column(length = 100)
    private String reply; //답글

    private int viewCount; // 조회수

    private int commentCnt; // 댓글 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberSeq")
    private Member member;

    /* 연관관계 필요합니다 */
    // @OneToMany
    @Transient
    private List<CommentData> comments; // 댓글 목록 -> 댓글

    @Transient
    private List<FileInfo> editorFiles;  // 에디터 첨부 파일

    @Transient
    private List<FileInfo> attachFiles;  // 첨부파일

    /* 나(게시글)를 찜한 멤버 - 보류
    * 이다은 - 1월 9일
    * */
    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> memberList=new ArrayList<>();

}
