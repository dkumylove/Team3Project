package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberInfo;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    //private final JoinValidator joinValidator;
    private final JoinService joinService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {


        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {

        joinService.process(form, errors);

        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {
        return utils.tpl("member/login");
    }

    @GetMapping("/info")
    @ResponseBody
    public void info() {
        MemberInfo memberInfo = (MemberInfo) SecurityContextHolder
                .getContext()
                .getAuthentication()  // 로그인 정보가 담겨있는 객체
                .getPrincipal();
        System.out.println("memberInfo = " + memberInfo);
    }

}
