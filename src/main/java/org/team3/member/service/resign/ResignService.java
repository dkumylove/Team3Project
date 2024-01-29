package org.team3.member.service.resign;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.team3.file.service.FileDeleteService;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ResignService {

    private final MemberRepository repository;
    private final HttpSession session;
    private final MemberUtil memberUtil;
    private final FileDeleteService fileDeleteService;

    public void resign() {
        Member member = memberUtil.getMember();
        member.setEnabled(false);
        member.setName("****");

        String gid = member.getGid();

        repository.saveAndFlush(member);
        fileDeleteService.delete(gid);

        session.invalidate();
    }
}