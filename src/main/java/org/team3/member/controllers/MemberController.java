package org.team3.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.member.service.FindIdService;
import org.team3.member.service.FindPwService;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberInfo;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    private final Utils utils;
    private final JoinService joinService;
    private final FindPwService findPwService;
    private final FindIdService findIdService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {

        commonProcess("join", model);
        model.addAttribute("EmailAuthVerified", false); // 이메일 인증여부 false로 초기화

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus sessionStatus) {

        commonProcess("join", model);

        joinService.process(form, errors);

        if(errors.hasErrors()) {
            return utils.tpl("member/join");
        }
        /* EmailAuthVerified 세션값 비우기 */
        sessionStatus.setComplete();
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
        List<String> addCss = new ArrayList<>(); // cdd추가

        if(mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");
        } else if (mode.equals("join")) {
            addCommonScript.add("fileManager");
            addScript.add("member/form");
            addScript.add("member/join");
            addCss.add("member/join");
        }else if(mode.equals("find_pw")) { // 비밀번호 찾기
            pageTitle = Utils.getMessage("비밀번호_찾기", "commons");
        } else if(mode.equals("find_id")){
            pageTitle = Utils.getMessage("아이디_찾기", "commons");
            addScript.add("member/findId");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }

    /**
     * 비밀번호 찾기 양식
     *
     * @param model
     * @return
     */
    @GetMapping("/find_pw")
    public String findPw(@ModelAttribute RequestFindPw form, Model model) {
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw");
    }

    /**
     * 비밀번호 찾기 처리
     *
     * @param model
     * @return
     */
    @PostMapping("/find_pw")
    public String findPwPs(@Valid RequestFindPw form, Errors errors, Model model, SessionStatus sessionStatus) {
        commonProcess("find_pw", model);

        findPwService.process(form, errors); // 비밀번호 찾기 처리

        if (errors.hasErrors()) {
            return utils.tpl("member/find_pw");
        }
        /* EmailAuthVerified 세션값 비우기 */
        sessionStatus.setComplete();
        // 비밀번호 찾기에 이상 없다면 완료 페이지로 이동
        return "redirect:/member/find_pw_done";
    }

    /**
     * 비밀번호 찾기 완료 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/find_pw_done")
    public String findPwDone(Model model) {
        commonProcess("find_pw", model);

        return utils.tpl("member/find_pw_done");
    }

    /**
     * 아이디 찾기 양식
     *
     * @param model
     * @return
     */
    @GetMapping("/find_id")
    public String findId(@ModelAttribute RequestFindId form, Model model) {
        commonProcess("find_id", model);
        model.addAttribute("EmailAuthVerified", false); // 이메일 인증여부 false로 초기화
        return utils.tpl("member/find_id");
    }

    /**
     * 아이디 찾기 처리
     *
     * @param model
     * @return
     */
    @PostMapping("/find_id")
    public String findIdPs(@Valid RequestFindId form, Errors errors, Model model, SessionStatus sessionStatus) {
        commonProcess("find_id", model);

        findIdService.process(form, errors); // 비밀번호 찾기 처리

        if (errors.hasErrors()) {
            return utils.tpl("member/find_id");
        }
        /* EmailAuthVerified 세션값 비우기 */
        sessionStatus.setComplete();
        // 비밀번호 찾기에 이상 없다면 완료 페이지로 이동
        return "redirect:/member/find_id_done";
    }

    /**
     * 아이디 찾기 완료 페이지
     *
     * @param model
     * @return
     */
    @GetMapping("/find_id_done")
    public String findIdDone(Model model) {
        commonProcess("find_id", model);

        return utils.tpl("member/find_id_done");
    }

}