package org.team3.board.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.team3.commons.entities.BaseMember;
import org.team3.file.entities.FileInfo;
import org.team3.member.Authority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class Board extends BaseMember {
    /**
     * Board entity 작업
     * 이기흥 - 1월 9일
     * 이지은 - 1월 9일
     */
    @Id
    private String bid; // 게시판 아이디

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    @Column(length = 60, nullable = false)
    private String bName; // 게시판 이름

    private boolean active; // 사용 여부
    private int rowsPerPage; // 한 페이지 게시글 수
    private int pageCountPc = 10; // 페이지 구간 개수
    private int pageCountMobile = 5; // Mobile 페이지 구간 개수
    private boolean useReply; // 답글
    private boolean useComment;  // 댓글
    private boolean useEditor; // 에디터
    private boolean useUploadImage;  // 이미지 첨부
    private boolean useUploadFile;  // 파일 첨부

    @Column(length = 10 , nullable = false)
    private String locationAfterWriting = "LIST"; // 글 작성 후 이동 위치

    @Column(length = 10, nullable = false)
    private String skin = "default"; // 스킨

    @Lob  // Large Object : 데이터베이스에 큰 객체(대용량 데이터)를 포함
    private String category;  // 카테고리

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority listAccessType = Authority.ALL; // 권한 설정 - 글목록

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority viewAccessType = Authority.ALL; // 권한 설정 - 글보기

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority writeAccessType = Authority.ALL; // 권한 설정 - 글쓰기

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority replyAccessType = Authority.ALL; // 권한 설정 - 답글

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Authority commentAccessType = Authority.ALL; // 권한 설정 - 댓글

    @Lob
    private String htmlTop; // 게시판 상단 HTML

    @Lob
    private String htmlBottom; // 게시판 하단 HTML

    @Transient
    private List<FileInfo> htmlTopImages; // 게시판 상단 Top 이미지

    @Transient
    private List<FileInfo> htmlBottomImages; // 게시판 하단 Bottom 이미지

    @Transient
    private FileInfo logo1; // 로고 이미지 1

    @Transient
    private FileInfo logo2; // 로고 이미지 2

    @Transient
    private FileInfo logo3; // 로고 이미지 3

    /**
     * 분류 List 형태로 변환
     * @return
     */
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        if(StringUtils.hasText(category)) {
            categories = Arrays.stream(category.trim().split("\\n"))
                    .map(s -> s.trim().replaceAll("\\r", ""))
                    .toList();
        }

        return categories;
    }
}