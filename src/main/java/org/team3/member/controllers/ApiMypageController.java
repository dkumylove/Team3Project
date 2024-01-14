package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.team3.member.service.ChangePasswordService;

@RestController("apimypagecontroller")
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class ApiMypageController {
    private ChangePasswordService mypageService;

/*
    @GetMapping("/changPw")
    public ResponseEntity<Map<String, String>> changepwd(@RequestParam("cntpwd") String cntpwd,
                                                         @RequestParam("newpwd") String newpwd,
                                                         @RequestParam("checkpwd") String checkpwd){
        Map<String, String> result = new HashMap<>();
        if(mypageService.checkPassword());
    }

 */


}
