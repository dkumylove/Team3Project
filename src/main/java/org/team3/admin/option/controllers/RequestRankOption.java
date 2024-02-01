package org.team3.admin.option.controllers;

import lombok.Data;
import org.team3.admin.option.entities.Options;

import java.util.List;

@Data
public class RequestRankOption {
    private boolean active=true; // 게시판 사용여부
    private String optionDetail; // 게시판 설명

    private String optionname;

    private int rank; //

    private int favorite; // 선택한 회원

}
