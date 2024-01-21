package org.team3.member.controllers;

import lombok.Data;
import org.team3.commons.RequestPaging;

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

}
