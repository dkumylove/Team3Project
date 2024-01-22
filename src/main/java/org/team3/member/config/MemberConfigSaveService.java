package org.team3.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.team3.board.entities.Board;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertException;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberConfigSaveService {

    private final Utils utils;
    private final MemberRepository memberRepository;
    public void saveList(List<Integer> chks) {
        if(chks == null || chks.isEmpty()) {
            throw new AlertException("수정할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
        }

        for(int chk : chks) {
            String userId = utils.getParam("userId");
            Member member = memberRepository.findByUserId(userId).orElse(null);
            if(member == null) continue;
            System.out.println("=================member"+member);
            boolean active = Boolean.parseBoolean(utils.getParam("act_" + chk));
            member.setAct(active);

        }

        memberRepository.flush();
    }
}
