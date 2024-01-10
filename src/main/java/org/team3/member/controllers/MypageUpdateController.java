package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.service.JoinService;
import org.team3.member.validator.currentUser;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/{id}")
public class MypageUpdateController {

    private static final String UPDATEFORM = "mypage/main"; // 수정폼
    private static final String UPDATEFORMURL = "/mypage/main";

    private final JoinService joinService;
    @GetMapping("") //수정폼
    public String mypageUpdateForm(@currentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute(new Profile(member));
        return UPDATEFORM; //수정폼
    }

    @PostMapping("") // 수정폼으로 요청 들어올 때
    public String updateProfile(@currentUser Member member, @Valid @ModelAttribute Profile profile, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            return UPDATEFORM; // 수정폼
        }

        joinService.updateProfile(member, profile);
        return "redirect:" + UPDATEFORMURL;
    }
}
