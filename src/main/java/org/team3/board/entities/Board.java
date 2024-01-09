package org.team3.board.entities;

import jakarta.persistence.*;
import org.team3.commons.entities.BaseMember;

public class Board extends BaseMember {
    /**
     * Board entity 작업
     * 이기흥 - 1월 9일
     * 이지은 - 1월 9일
     */
    @Id
    @GeneratedValue
    private Long bId; // 게시판 아이디
    private String bName; // 게시판 이름
    private boolean active; // 사용 여부
    private int bRowsPerPage; // 한 페이지 게시글 수
    private int pageCountPc; // 페이지 구간 개수
    private boolean useReply; // 답글
    private boolean useComment;  // 댓글
    private boolean useEditor; // 에디터
    private boolean useUploadImage;  // 이미지 첨부
    private boolean useUploadFile;  // 파일 첨부
    private boolean locationAfterWriting; // 글작성 후 이동

    @Enumerated(EnumType.STRING)
    @Column(length=15)
    private SkinType skin;  // 스킨여부

    @Lob  // Large Object : 데이터베이스에 큰 객체(대용량 데이터)를 포함
    private String category;  // 카테고리

    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
    @Column(length=10, nullable = false)
    private BoardAuthority listAccessType = BoardAuthority.ALL;  // 글목록 권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private BoardAuthority viewAccessType = BoardAuthority.ALL;  // 글보기 권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private BoardAuthority writeAccessType = BoardAuthority.ALL;  // 글쓰기 권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private BoardAuthority replyAccessType = BoardAuthority.ALL;  // 답글 권한

    @Enumerated(EnumType.STRING)
    @Column(length=10, nullable = false)
    private BoardAuthority commentAccessType = BoardAuthority.ALL;  // 댓글 권한
}