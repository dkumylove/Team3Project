package org.team3.admin.member.controllers;


import lombok.Data;
import org.team3.member.Authority;
import org.team3.member.controllers.MemberSearch;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class MemberSearchOptions extends MemberSearch {

    private String createSdate;
    private String createEdate;
    private Authority authority;
    private String userId;
    private List<String> userIds;
    private String username;
    private String nickname;

    private boolean act; // 활동여부
    private boolean active; // 탈퇴여부

    private String sopt; // 검색옵션
    private String skey; // 검색 키워드

}
