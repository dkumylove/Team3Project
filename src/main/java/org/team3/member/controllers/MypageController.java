package org.team3.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController implements ExceptionProcessor {

    private final Utils utils;

    /**
     * 마이페이지 main
     * default (profile)
     * @return
     */
    @GetMapping
    public String index() {

        return utils.tpl("mypage/profile");
    }

    /**
     * 내 프로필
     * @return
     */
    @GetMapping("/profile")
    public String profile() {

        return utils.tpl("mypage/profile");
    }

    /**
     * 내 활동
     * @return
     */
    @GetMapping("/myBoard")
    public String myBoard() {

        return utils.tpl("mypage/myBoard");
    }

    /**
     * 팔로우
     * @return
     */
    @GetMapping("/follow")
    public String follow() {

        return utils.tpl("mypage/follow");
    }

    /**
     * 회원탈퇴
     * @return
     */
    @GetMapping("/deleteMember")
    public String deleteMember() {

        return utils.tpl("mypage/deleteMember");
    }
}

