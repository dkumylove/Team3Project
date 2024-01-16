package org.team3.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;

@Service
@RequiredArgsConstructor
public class FollowService {

    /**
     * Follow 기능
     * 1월 16일 이지은
     */

    private final MemberRepository memberRepository;

    public void followUser(String followerUsername, String followingUsername) {

    }

}
