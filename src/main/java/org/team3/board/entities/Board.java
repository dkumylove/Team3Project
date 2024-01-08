package org.team3.board.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jdt.internal.compiler.parser.AbstractCommentParser;

@Entity
@Data
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 자동으로 생성
public class Board {
    @Id
    private Long bId; // 게시판 번호

    @Column(length = 30, nullable = false)
    private String bTitle; // 글 제목

    @Column(length = 30, nullable = false)
    private String bCategory; // 카테고리

    @Column(length = 100, nullable = false)
    private String bReply; // 댓글
    
    @Column(length = 100, nullable = false)
    private String bContent; // 내용
}