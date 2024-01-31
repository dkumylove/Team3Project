package org.team3.admin.option.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.team3.admin.option.Repository.OptionRepository;
import org.team3.admin.option.controllers.OptionSearch;
import org.team3.admin.option.entities.Options;
import org.team3.admin.option.entities.QOptions;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.commons.Utils;
import org.team3.member.Authority;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Member;
import org.team3.member.entities.QAuthorities;
import org.team3.member.entities.QMember;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
@Transactional
public class OptionConfigInfoService {

    private final OptionRepository optionRepository;
    private final HttpServletRequest request;

    /**
     * 옵션 네임으로 불러오기
     * @param optionname
     * @return
     */
    public Options getOption(String optionname){
        Options options = optionRepository.findById(optionname).orElse(null);
        return options;
    }


    /**
     * 옵션 이름만 가져오는 list
     * @return
     */
    public List<String> getOptionname(){
        List<String> selectOptionname = new ArrayList<>();
        List<Options> all = optionRepository.findAll();
        if(!all.isEmpty() && all.size()>0){
            for(int i = 0; i<all.size(); i++) {
                selectOptionname.add(all.get(i).getOptionname());
            }
        }
        return selectOptionname;
    }

    /**
     * 사용중인 리스트 불러오기
     * @return
     */
    public List<Options> getOptionList(){
        List<Options> all = optionRepository.findByActiveTrue();
        return all;
    }

    /**
     * 보조지표 설정 목록
     *
     * @param search
     * @return
     */
    public ListData<Options> getList(@Nullable OptionSearch search, boolean isAll) {
        int page = Utils.onlyPositiveNumber(search.getPage(), 1);
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20);

        QOptions options = QOptions.options;
        BooleanBuilder andBuilder = new BooleanBuilder();


        /* 카테고리 조회 S */
        String[] category = search.getCategory();
        if (category != null && category.length > 0) {
            BooleanExpression[] categoryExpressions = new BooleanExpression[category.length];
            for (int i = 0; i < category.length; i++) {
                categoryExpressions[i] = options.category.contains(category[i]);
            }
            andBuilder.andAnyOf(categoryExpressions);
        }
        /* 카테고리 조회 E */


        /* 사용여부 조회 S */
        /* 사용여부 조회 E */


        /* 검색 조건 처리 S */
        String optionname = search.getOptionname(); // 옵션이름 id
        List<String> categories = search.getCategories(); // 카테고리


        String sopt = search.getSopt(); // 옵션
        sopt = StringUtils.hasText(sopt) ? sopt.trim() : "ALL"; // 없으면 전체
        String skey = search.getSkey(); // 키워드

        if(StringUtils.hasText(optionname)) {
            andBuilder.and(options.optionname.contains(optionname.trim()));
        }

        if(categories != null && !categories.isEmpty()) {
            andBuilder.and(options.category.in(categories));
        }

        // 조건별 키워드 검색
        if(StringUtils.hasText(skey)) {
            skey = skey.trim();

            BooleanExpression cond1 = options.optionname.contains(skey);
            BooleanExpression cond2 = options.category.contains(skey);

            if(sopt.equals("name")) {
                andBuilder.and(cond1);
            } else if(sopt.equals("cate")) {
                andBuilder.and(cond2);
            } else{
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(cond1)
                        .or(cond2);
                andBuilder.and(orBuilder);
            }
        }
        /* 검색 조건 처리 E */



        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(desc("createdAt")));
        Page<Options> data = optionRepository.findAll(andBuilder, pageable);

        Pagination pagination = new Pagination(page, (int)data.getTotalElements(), limit, 20, request);

        return new ListData<>(data.getContent(), pagination);
    }

}
