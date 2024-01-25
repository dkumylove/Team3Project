package org.team3.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.commons.Utils;
import org.team3.file.entities.FileInfo;
import org.team3.file.service.FileInfoService;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.entities.QMember;
import org.team3.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 조회
 */
@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final FileInfoService fileInfoService;
    /**
     * 요청데이터....
     */
    private final HttpServletRequest request;
    /**
     * 목록을 가져올 때 지연로딩이 걸려있으므로 n+1이 발생할 수 있음
     */
    private final EntityManager em;

    /**
     * 반환값이 UserDetails 임 -> 구현체로서 반환값만 넣어주면 됨
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username) // 아이디 조회
                        .orElseThrow(()-> new UsernameNotFoundException(username)); // 예외

        List<Authorities> tmp = member.getAuthorities();
        List<SimpleGrantedAuthority> authorities = null;
        // map은 변환 메서드
        // 권한이 문자열이기때문에
        // i.getAuthority().name() : enum상수 그대로 인식할 수 없기 때문에 문자열로넣어주어야함!
        if(tmp != null){
            authorities = tmp.stream()
                    .map(i-> new SimpleGrantedAuthority(i.getAuthority().name()))
                    .toList();
        }

        // 추가 정보 처리 (원래 있던 프로필 이미지 처리부분 하단에 분리)
        addMemberInfo(member);

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .userId(member.getUserId())
                .member(member)
                .authorities(authorities)
                .build();
    }

    /**
     * 회원목록
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search){
        int page = Utils.onlyPositiveNumber(search.getPage(), 1); // 페이지 구간
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20); // 페이지당 레코드 갯수
        int offset = (page-1) * limit;

        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        // 주로 정렬할 때 쓰임
        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");

        // 회원목록을 가져옴
        // 레코드는 0번째 부터...시작함
        List<Member> items = new JPAQueryFactory(em).selectFrom(member)
                .leftJoin(member.authorities).fetchJoin()
                .where(andBuilder).limit(limit).offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt"))).fetch();

        /* 페이징 처리 R */
        int total = (int) memberRepository.count(andBuilder); // items.size() 얘랑 위의 크기랑 다른가..?
        System.out.println(total);
        // total = 12345;
        Pagination pagination = new Pagination(page, total, 10, limit, request);
        /* 페이징 처리 E */

        return new ListData<>(items, pagination);
    }

    /**
     * 회원 추가 정보 처리
     * - loadUserByUsername() 안에 있던 내용 분리함
     * 1월22일 이지은
     * @param member
     */
    public void addMemberInfo(Member member) {
        /* 프로필 이미지 처리 S */
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if (files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }
        /* 프로필 이미지 처리 E */
    }


    public Member getMember(String userId){

        Member member = memberRepository.findByUserId(userId).orElse(null);

        if(member != null){

            addMemberInfo(member);

        }

        return member;

    }

}



