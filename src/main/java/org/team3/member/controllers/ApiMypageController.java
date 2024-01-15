package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.commons.rests.JSONData;
import org.team3.member.service.ChangePasswordService;

import java.util.Map;

@RestController("apimypagecontroller")
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ApiMypageController {
    private ChangePasswordService changePasswordService;


    /**
     * 현재 비밀번호 일치여부
     * @param cntpwd 현재비밀번호
     * @return
     */
//    @GetMapping("/changPw")
//    public JSONData<Object> changepwd(@RequestParam("cntpwd") String cntpwd){
//        JSONData<Object> data = new JSONData<>();
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails user = (UserDetails) authentication.getPrincipal();
//        String username = user.getUsername(); // 이메일
//        boolean result = changePasswordService.checkPassword(username, cntpwd);
//        if(result){
//            data.setSuccess(result);
//        } else {
//            data.setSuccess(false);
//        }
//        return data;
//    }

    /**
     *
     */


}
