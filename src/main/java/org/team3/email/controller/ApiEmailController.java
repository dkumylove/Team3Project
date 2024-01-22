package org.team3.email.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.team3.commons.rests.JSONData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team3.email.service.EmailVerifyService;
import org.team3.member.repositories.MemberRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailVerifyService verifyService;
    private final MemberRepository memberRepository;


    /**
     * 이메일 인증 코드 발급 - 아이디 찾기 시
     *
     * @param email : 이메일
     * @param name : 회원명
     * @param request
     * @return
     */
    @GetMapping("/verify/findid")
    public JSONData<Object> sendVerifyEmailId(@RequestParam("email") String email,
            @RequestParam("name") String name, HttpServletRequest request) {
        JSONData<Object> data = new JSONData<>();

        /** 이메일과 회원명이 일치하는 지 확인 **/
        if(memberRepository.existsByEmailAndName(email,name)) {
            boolean result = verifyService.sendCode(email, request);
            data.setSuccess(result);
        }else{
            data.setSuccess(false);
        }
        return data;
    }

    /**
     * 이메일 인증 코드 발급 : 회원가입시
     *
     * @param email : 이메일 확인
     * @return
     */
    @GetMapping("/verify")
    public JSONData<Object> sendVerifyEmail(@RequestParam("email") String email, HttpServletRequest request) {
        JSONData<Object> data = new JSONData<>();

        /**
         * type : auth - 이메일 인증 번호 전송
         *      : find_id : 아이디 전송
         */

        /*
        String type = params.type();
        String email = params.email();
        String userId = params.userId();
        type = StringUtils.hasText(type) ? type : "auth";

        Map<String, Object> tplData = new HashMap<>();
        tplData.put("userId", userId);
         */

        boolean result = verifyService.sendCode(email, request);
        data.setSuccess(result);

        return data;
    }

    /**
     * 발급받은 인증코드와 사용자 입력 코드의 일치 여부 체크
     *
     * @param authNum
     * @return
     */

    @GetMapping("/auth_check")
    public JSONData<Object> checkVerifiedEmail(@RequestParam("authNum") int authNum) {
        JSONData<Object> data = new JSONData<>();

        boolean result = verifyService.check(authNum);
        data.setSuccess(result);

        return data;
    }
}
