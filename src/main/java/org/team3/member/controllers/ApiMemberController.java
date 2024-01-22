package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.team3.commons.ExceptionRestProcessor;
import org.team3.commons.rests.JSONData;
import org.team3.member.MemberUtil;
import org.team3.member.repositories.MemberRepository;
import org.team3.member.service.follow.FollowService;

import java.security.Principal;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController implements ExceptionRestProcessor {

    private final MemberRepository memberRepository;
    private final FollowService followService;

    /**
     * 이메일 중복 여부 체크
     * @param email
     * @return
     */
    @GetMapping("/email_dup_check")
    public JSONData<Object> duplicateEmailCheck(@RequestParam("email") String email) {
        boolean isExists = memberRepository.existsByEmail(email);

        JSONData<Object> data = new JSONData<>();
        data.setSuccess(isExists);

        return data;
    }
  
    /**
     * 아이디 중복 여부 체크
     * @param userId
     * @return
     */
    @GetMapping("/userIdcheck")
    public JSONData<Object> duplicateUserIdCheck(@RequestParam("userId") String userId) {
        boolean isExists = memberRepository.existsByUserId(userId);
        System.out.println("isExists"+isExists);
        JSONData<Object> data = new JSONData<>();
        if(isExists){
            data.setSuccess(false);
        } else {
            data.setSuccess(true);
        }
        return data;
    }

    /**
     * follow
     * 1월 22일 이지은
     * @param userId
     * @return
     */
    @GetMapping("/follow/{userId}")
    public JSONData<Object> follow(@PathVariable("userId") String userId) {
        followService.follow(userId);

        return new JSONData<>();
    }

    /**
     * unfollow
     * 1월 22일 이지은
     * @param userId
     * @return
     */
    @GetMapping("/unfollow/{userId}")
    public JSONData<Object> unfollow(@PathVariable("userId") String userId) {
        followService.unfollow(userId);

        return new JSONData<>();
    }


}