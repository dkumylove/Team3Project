package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberInfo;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    //private final JoinValidator joinValidator;
    private final JoinService joinService;
    //private final MemberUtil memberUtil;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {

        commonProcess("join", model);

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model) {

        commonProcess("join", model);

        joinService.process(form, errors);

        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);
        return utils.tpl("member/login");
    }

    private void commonProcess(String mode, Model model) {
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addCommonScript = new ArrayList<>();  // 공통 자바스크립트
        List<String> addScript = new ArrayList<>();  // 프론트 자바 스크립트

        if(mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");
        } else if (mode.equals("join")) {
            addCommonScript.add("fileManager");
            addScript.add("member/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}