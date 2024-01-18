package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.team3.commons.ExceptionRestProcessor;
import org.team3.commons.rests.JSONData;
import org.team3.member.MemberUtil;
import org.team3.member.repositories.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class ApiMemberController implements ExceptionRestProcessor {
    private final MemberRepository memberRepository;
    private final MemberUtil memberUtil;
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
     * 회원정보 업데이트
     */
    @GetMapping("/update")
    public void updateMemberInfo() {
        memberUtil.update();
    }
}