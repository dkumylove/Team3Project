package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.team3.admin.board.controllers.RequestBoardConfig;
import org.team3.admin.member.controllers.MemberSearchOptions;
import org.team3.board.entities.Board;
import org.team3.board.service.config.BoardNotFoundException;
import org.team3.file.entities.FileInfo;
import org.team3.member.Authority;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberConfigInfoService {
    private final MemberRepository memberRepository;

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
    public MemberSearchOptions getForm(String userId) {
        Member member = get(userId);

        MemberSearchOptions form = new ModelMapper().map(member, MemberSearchOptions.class);
        form.setActive(true);
        form.setAuthority(Authority.ALL);
        form.setCreateEdate(form.getCreateEdate());
        form.setCreateEdate(form.getCreateEdate());

        return form;
    }


}
