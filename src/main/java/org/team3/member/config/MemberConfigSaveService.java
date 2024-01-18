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
//    public void saveList(List<Integer> chks) {
//        if(chks == null || chks.isEmpty()) {
//            throw new AlertException("수정할 회원을 선택하세요.", HttpStatus.BAD_REQUEST);
//        }
//
//        for(int chk : chks) {
//            //String userId = utils.getParam("userId_" + chk);
//            Member member = memberRepository.findById(seq).orElse(null);
//            if(member == null) continue;
//
//            boolean active = Boolean.parseBoolean(utils.getParam("active_" + chk));
//            //member.setActive(active);
//
//            int listOrder = Integer.parseInt(utils.getParam("listOrder_" + chk));
//            //member.setListOrder(listOrder);
//        }
//
//        memberRepository.flush();
//    }
}
