package org.team3.member.controllers;

import lombok.Data;

/**
 * 검색 데이터 -> 요청 데이터!
 * 검색 데이터를 담을 커맨드 객체가 필요함
 */
@Data
public class MemberSearch {
    protected int page = 1;
    protected int limit = 20;
}
