package org.team3.admin.option.controllers;

import lombok.Data;
import org.team3.commons.RequestPaging;

import java.util.List;

@Data
public class OptionSearch extends RequestPaging {

    private String optionname;
    private List<String> optionnames;

    private String[] category;

    private List<String> categories;

    private String active; // 사용여부
    private String sopt; // 검색옵션
    private String skey; // 검색 키워드
}
