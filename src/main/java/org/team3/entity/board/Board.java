//package org.team3.entity.board;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.team3.entity.abstractCommon.BaseMember;
//
//@Data
//@Builder
//@Entity
//@NoArgsConstructor @AllArgsConstructor
//public class Board extends BaseMember {
//
//
//    @Id  // primary key
//    @Column(length=30)
//    private String bid;  // 게시판 아이디
//
//    @Column(length=60, nullable = false)
//    private String bName;  // 게시판 이름
//
//    private boolean active;  // 사용여부
//
//    private int bRowsPerPage;  // 1페이지 게시글 수
//
//    private int pageCountPc;  // 페이지 구간 갯수
//
//    private boolean useReply;  // 답글
//
//    private boolean useComment;  // 댓글
//
//    private boolean useEditor;  // 에디터
//
//    private boolean useUploadImage;  // 이미지 첨부
//
//    private boolean useUploadFile;  // 파일 첨부
//
//    private boolean locationAfterWriting;  // 글작성 후 이동
//
//    @Enumerated(EnumType.STRING)
//    @Column(length=15)
//    private SkinType skin;  // 스킨여부
//
//    @Lob  // Large Object : 데이터베이스에 큰 객체(대용량 데이터)를 포함
//    private String category;  // 카테고리
//
//    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
//    @Column(length=10, nullable = false)
//    private BoardAuthority listAccessType = BoardAuthority.ALL;  // 글목록 권한
//
//    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
//    @Column(length=10, nullable = false)
//    private BoardAuthority viewAccessType = BoardAuthority.ALL;  // 글보기 권한
//
//    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
//    @Column(length=10, nullable = false)
//    private BoardAuthority writeAccessType = BoardAuthority.ALL;  // 글쓰기 권한
//
//    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
//    @Column(length=10, nullable = false)
//    private BoardAuthority replyAccessType = BoardAuthority.ALL;  // 답글 권한
//
//    @Enumerated(EnumType.STRING)  // 열거형(Enum) 타입을 String타입으로 매핑을 지정
//    @Column(length=10, nullable = false)
//    private BoardAuthority commentAccessType = BoardAuthority.ALL;  // 댓글 권한
//
//}
