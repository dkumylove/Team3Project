package org.team3.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.board.entities.Board;
import org.team3.board.repositories.BoardRepository;
import org.team3.board.service.config.BoardConfigInfoService;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertException;
import org.team3.file.service.FileDeleteService;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberConfigDeleteService {
    private final MemberRepository memberRepository;
    private final MemberConfigInfoService configInfoService;
    private final FileDeleteService fileDeleteService;
    private final Utils utils;

    /**
     * 게시판 삭제
     *
     * @param bid : 게시판 아이디
     */
    public void delete(String userId) {
        Member member = configInfoService.get(userId);

        String gid = member.getGid();

        memberRepository.delete(member);

        memberRepository.flush();

        fileDeleteService.delete(gid);
    }

    public void deleteList(List<Integer> chks) {
        if(chks == null || chks.isEmpty()) {
            throw new AlertException("삭제할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for(int chk : chks) {
            String userId = utils.getParam("seq_" + chk); // userId
            delete(userId);
        }
    }
}
