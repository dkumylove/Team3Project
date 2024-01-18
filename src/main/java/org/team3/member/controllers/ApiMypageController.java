package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.MemberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.commons.rests.JSONData;
import org.team3.member.MemberUtil;
import org.team3.member.service.ChangePasswordService;

import java.util.Map;

@RestController("apimypagecontroller")
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ApiMypageController {
    private final ChangePasswordService changePasswordService;
    private final MemberUtil memberUtil;


    /**
     * 현재 비밀번호 일치여부
     * @param cntpwd 현재비밀번호
     * @return
     */
    @GetMapping("/changePwCheck")
    public JSONData<Object> changepwd(@RequestParam("cntpwd") String cntpwd){
        JSONData<Object> data = new JSONData<>();
        // 현재 사용자 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println(authentication);

        boolean result = changePasswordService.checkPassword(authentication.getName(), cntpwd);
        System.out.println("result" + result);

        if(result){
            data.setSuccess(result);
        } else {
            data.setSuccess(false);
        }
        return data;
    }

    /**
     *
     */


}
