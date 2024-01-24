package org.team3.member.config;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.team3.admin.board.controllers.RequestBoardConfig;
import org.team3.admin.member.controllers.RequestMemberConfig;
import org.team3.board.entities.Board;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertException;
import org.team3.file.service.FileUploadService;
import org.team3.member.Authority;
import org.team3.member.entities.Authorities;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;
import org.team3.member.service.MemberNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {

    private final Utils utils;
    private final MemberRepository memberRepository;
    private final FileUploadService fileUploadService;

    public void save(RequestMemberConfig form) {
        String userId = form.getUserId();
        String mode = form.getMode();
        mode = StringUtils.hasText(mode) ? mode : "add";

        Member member = memberRepository.findByUserId(userId).orElseGet(Member::new);

        if(mode.equals("add")) { // 회원 등록시 gid 등록 -> 수정시에 변경 x
            member.setGid(form.getGid());
        }
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
        member.setNickName(form.getNickName());
        member.setUserId(form.getUserId());
        member.setProfileImage(form.getProfileImage());
        member.setAct(form.isAct());



        if(form.getManagerType().equals("ADMIN")){
            member.setAuthority(Authority.ADMIN);
        } else {
            member.setAuthority(Authority.USER);
        }



        memberRepository.saveAndFlush(member);

        // 파일 업로드 완료 처리
        fileUploadService.processDone(member.getGid());
    }






    public void saveList(List<Integer> chks) {
        if(chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for(int chk : chks) {
            String userId = utils.getParam("seq_" + chk); // userId
            Member member = memberRepository.findByUserId(userId).orElse(null);
            if(member == null) continue;
            System.out.println("=================member"+member);
            boolean active = Boolean.parseBoolean(utils.getParam("act_" + chk));
            member.setAct(active);
        }

        memberRepository.flush();
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
