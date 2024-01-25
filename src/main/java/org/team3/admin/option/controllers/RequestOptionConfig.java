package org.team3.admin.option.controllers;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.team3.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestOptionConfig {

    private String mode = "add";

    private String gid = UUID.randomUUID().toString();

    private int listOrder; //

    @NotBlank
    private String optionname; // 옵션 name

    @NotBlank
    private String category; // 기타가 디폴트 값

    private boolean active; // 사용 여부

    private boolean useComment; // 댓글 사용 여부

    private String locationAfterWriting = "list"; // 글 작성 후 이동 위치

    private boolean showListBelowView; // 글 보기 하단 목록 노출 여부

    private String listAccessType = "ALL"; // 권한 설정 - 글목록

    private String viewAccessType = "ALL"; // 권한 설정 - 글보기

    private String commentAccessType = "ALL"; // 권한 설정 - 댓글

    private String htmlTop; // 게시판 상단 HTML
    private String htmlBottom; // 게시판 하단 HTML

}
