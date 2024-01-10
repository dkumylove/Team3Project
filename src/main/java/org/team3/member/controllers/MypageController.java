package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.member.entities.Member;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypage/{id}")
@RequiredArgsConstructor
public class MypageController {

    Member member = new Member();

    @GetMapping("/")
    public String mypage(Model model) {
        model.addAttribute(new Member());
        return "mypage/main";
    }

    @GetMapping("/changeName")
    public String changeName(Model model) {

        return "mypage/main";
    }
}
