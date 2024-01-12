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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class ApiEmailController {

    private final EmailVerifyService verifyService;

    /**
     * 이메일 인증 코드 발급
     *
     * @param email
     * @return
     */
    @GetMapping("/verify")
    public JSONData<Object> sendVerifyEmail(EmailData params, HttpServletRequest request) {
        JSONData<Object> data = new JSONData<>();

        /**
         * type : auth - 이메일 인증 번호 전송
         *      : find_id : 아이디 전송
         */
        String type = params.type();
        String email = params.email();
        String userId = params.userId();
        type = StringUtils.hasText(type) ? type : "auth";

        Map<String, Object> tplData = new HashMap<>();
        tplData.put("userId", userId);

        boolean result = verifyService.sendCode(email, request, type, tplData);
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