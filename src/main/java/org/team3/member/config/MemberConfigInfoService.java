package org.team3.member.config;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.team3.admin.board.controllers.BoardSearch;
import org.team3.admin.member.controllers.RequestMemberConfig;
import org.team3.board.entities.Board;
import org.team3.board.entities.QBoard;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.commons.Utils;
import org.team3.member.Authority;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.entities.QAuthorities;
import org.team3.member.entities.QMember;
import org.team3.member.repositories.MemberRepository;
import org.team3.member.service.MemberNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MemberConfigInfoService {


    private final MemberRepository memberRepository;
    private final HttpServletRequest request;

    /**
     * 게시판 설정 목록
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(@Nullable MemberSearch search, boolean isAll) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QMember member = QMember.member;
        QAuthorities authorities = QAuthorities.authorities;
        BooleanBuilder andBuilder = new BooleanBuilder();

        
        /* 탈퇴/제한 조회 S */
        /* 탈퇴/제한 조회 E */


        /* 가입구분 조회 S */
        String managerType = search.getManagerType();
        if(StringUtils.hasText(managerType)){
            if(managerType.equals("admin")){
                andBuilder.and(member.authority.eq(Authority.ADMIN))
                    .or(member.authorities.any().authority.in(Authority.ADMIN));
            } else if(managerType.equals("user")) {
                andBuilder.and(member.authority.eq(Authority.USER))
                    .or(member.authorities.any().authority.in(Authority.USER));
            } else {
                andBuilder.and(member.authority.in(Authority.ADMIN, Authority.USER)
                        .or(member.authorities.any().authority.in(Authority.ADMIN, Authority.USER)));
            }
        }

//        boolean admin = search.isAdmin();
//        if(admin){
//            andBuilder.and(member.authority.eq(Authority.ADMIN))
//                    .or(member.authorities.any().authority.in(Authority.ADMIN));
//        }
//
//        if(!admin){
//            andBuilder.and(member.authority.eq(Authority.USER))
//                    .or(member.authorities.any().authority.in(Authority.USER));
//        }
        /* 가입구분 조회 E */

        /* 정지/활동 조회 S */
//        boolean act = search.isAct();
//        if(act) {
//            andBuilder.and(member.act.eq(act));
//        }
//
//        if(!act) {
//            andBuilder.and(member.act.eq(false));
//        }
        /* 정지/활동 조회 E */


        /* 정지/활동 조회 S */
        String act = search.getAct();
        if(StringUtils.hasText(act)){
            if(act.equals("act")){
                andBuilder.and(member.act.eq(true));
            } else if(act.equals("stop")){
                andBuilder.and(member.act.eq(false));
            } else {
                andBuilder.and(member.act.in(true, false));
            }
        }

        /* 정지/활동 조회 E */


        /* 가입일 조회 S */
        String createSdate = search.getCreateSdate();
        String createEdate = search.getCreateEdate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate sDate = null;
        LocalDate eDate = null;


        if(StringUtils.hasText(createSdate)){
            sDate= LocalDate.parse(createSdate, formatter);
            andBuilder.and(member.createdAt.after(sDate.atStartOfDay()));
        }

        if(StringUtils.hasText(createEdate)){
            eDate= LocalDate.parse(createEdate, formatter);
            andBuilder.and(member.createdAt.before(eDate.plusDays(1).atStartOfDay()));
        }
        /* 가입일 조회 E */


        /* 검색 조건 처리 S */
        String userId = search.getUserId(); // 아이디는 유니크 낫널
        List<String> usernames = search.getUsernames(); // 회원명들
        List<String> nicknames = search.getNicknames(); //  닉네임들

        String sopt = search.getSopt(); // 옵션
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL"; // 없으면 전체
        String skey = search.getSkey(); // 키워드

        if(StringUtils.hasText(userId)) {
            andBuilder.and(member.userId.contains(userId.trim()));
        }

        if(usernames != null && !usernames.isEmpty()) {
            andBuilder.and(member.name.in(usernames));
        }

        if(nicknames != null && !nicknames.isEmpty()) {
            andBuilder.and(member.nickName.in(nicknames));
        }

        // 조건별 키워드 검색
        if(StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression cond1 = member.userId.contains(skey);
            BooleanExpression cond2 = member.name.contains(skey);
            BooleanExpression cond3 = member.nickName.contains(skey);

            if(sopt.equals("userId")) {
                andBuilder.and(cond1);
            } else if(sopt.equals("username")) {
                andBuilder.and(cond2);
            } else if(sopt.equals("nickname")) {
                andBuilder.and(cond3);
            } else{
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(cond1)
                        .or(cond2).or(cond3);
                andBuilder.and(orBuilder);
            }
        }
        /* 검색 조건 처리 E */



        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Member> data = memberRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), limit, 10, request);

        return new ListData<>(data.getContent(), pagination);
    }



    /**
     * 회원 설정 조회
     *
     * @param
     * @return
     */
    public Member get(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }


    public Member get(Long seq) {
        Member member = memberRepository.findById(seq)
                .orElseThrow(MemberNotFoundException::new);


        return member;
    }

//    public MemberSearchOptions getForm(String userId) {
//        Member member = get(userId);
//
//        MemberSearchOptions form = new ModelMapper().map(member, MemberSearchOptions.class);
//        form.setActive(true);
//        form.setAuthority(Authority.ALL);
//        form.setCreateEdate(form.getCreateEdate());
//        form.setCreateEdate(form.getCreateEdate());
//
//        return form;
//    }
    

    public RequestMemberConfig getForm(String userId) {
        Member member = get(userId);

        RequestMemberConfig form = new ModelMapper().map(member, RequestMemberConfig.class);
        form.setAct(true);
        form.setAgree(true);
        form.setMode("edit");

        return form;
    }


}
