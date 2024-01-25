package org.team3.admin.option.controllers;

import lombok.Data;
import org.team3.commons.RequestPaging;

import java.util.List;

@Data
public class OptionSearch extends RequestPaging {

    private String optionname;
    private List<String> optionnames;

    private boolean active;

    private String sopt; // 검색옵션
    private String skey; // 검색 키워드
}
