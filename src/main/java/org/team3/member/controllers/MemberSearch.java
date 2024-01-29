package org.team3.member.controllers;

import lombok.Data;
import org.team3.commons.RequestPaging;
import org.team3.member.Authority;

import java.util.List;

/**
 * 검색 데이터 -> 요청 데이터!
 * 검색 데이터를 담을 커맨드 객체가 필요함
 *
 * 공통기능을 뻄 -> commons/RequestPaging
 * protected int page = 1;
 * protected int limit = 20;
 */
@Data
public class MemberSearch extends RequestPaging {

    private String createSdate;
    private String createEdate;


    private String userId;
    private List<String> nicknames;
    private List<String> usernames;
    private String username;
    private String nickname;

    private String managerType; // 관리자면 admin, 아니면 user
    private String act; // 활동여부 활동하면 act, 아니면 stop
    private String resign; // 탈퇴여부

    private String sopt; // 검색옵션
    private String skey; // 검색 키워드

}
