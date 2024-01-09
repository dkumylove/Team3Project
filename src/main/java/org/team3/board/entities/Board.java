package org.team3.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Board {
    /*
     * Board entity 작업
     * 이기흥 - 1월 9일
     */
    @Id
    @GeneratedValue
    private Long bId; // 게시판 아이디
    private String bName; // 게시판 이름
    private boolean active; // 사용 여부
    private int bRowsPerPage; // 한 페이지 게시글 수
    private int pageCountPc; // 페이지 구간 개수
    private boolean useReply; // 답글
    private boolean useEditor; // 에디터
    private boolean locationAfterWriting; // 글작성 후 이동
}