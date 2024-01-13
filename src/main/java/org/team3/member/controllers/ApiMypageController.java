package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team3.commons.rests.JSONData;
import org.team3.member.service.MypageService;

import java.util.HashMap;
import java.util.Map;

@RestController("apimypagecontroller")
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ApiMypageController {
    private MypageService mypageService;

    /* 작성 중
    @GetMapping("/changPw")
    public ResponseEntity<Map<String, String>> changepwd(@RequestParam("cntpwd") String cntpwd,
                                                         @RequestParam("newpwd") String newpwd,
                                                         @RequestParam("checkpwd") String checkpwd){
        Map<String, String> result = new HashMap<>();
        if(mypageService.checkPassword());
    }

     */
}
