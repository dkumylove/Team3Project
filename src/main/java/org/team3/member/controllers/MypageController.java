package org.team3.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.team3.admin.member.controllers.MemberSearchOptions;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Utils;
import org.team3.member.entities.Member;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberInfoService;
import org.team3.member.service.MemberService;
import org.team3.member.service.MypageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.team3.member.entities.QMember.member;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;
    private final MypageService mypageService;

    // 마이페이지
    @GetMapping
    public String mypage(@ModelAttribute MemberSearchOptions search, Model model) {

        ListData<Member> data = memberInfoService.getList(search);
        System.out.println(data.getItems());

        model.addAttribute("memberList", data.getItems()); // 목록

        return utils.tpl("mypage/profile");
    }

    @GetMapping("/profile")
    public String profileForm() {
        return utils.tpl("mypage/profile");
    }


    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        model.addAttribute("subMenuCode", mode);
    }

    @PostMapping("/profile")
    public String profile(@ModelAttribute Member member) {
        memberService.save(member);
        return utils.tpl("mypage/profile");
    }

    // 닉네임 수정
    @GetMapping("/changeNickname")
    public String changeNicknameForm() {
        return utils.tpl("mypage/changeNickname");
    }

    @PostMapping("/changeNickname")
    public String changeNickname(@ModelAttribute Member member) {
        memberService.updateMemberNickname(member.getNickName());
        return utils.tpl("mypage/profile");
    }

    // 비밀번호 수정
    @GetMapping("/changePw")
    public String changePwForm(@ModelAttribute RequestChangePw requestChangePw) {

        return utils.tpl("mypage/changePw");
    }

    /* 잠시 주석 걸어둘게요 - 이다은 1월 13일
    @PostMapping("changePw")
    public String changePw(@ModelAttribute Member member, HttpSession httpSession) {
        String newPw = memberService.updateMemberPassword(member.getPassword());
        int result = Integer.parseInt(memberService.updateMemberPassword(member.getPassword()));
        //loginUser의 비밀번호 바꿔주기
        if (result > 0) {
            ((Member) httpSession.getAttribute("memberPw")).setPassword(newPw);
        }
        return utils.tpl("mypage/profile");
    }

     */

    @PreAuthorize("isAuthenticated()")
    @PostMapping("changePw")
    public String changePw(@Valid RequestChangePw requestChangePw, Errors errors) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(errors.hasErrors()){
            return utils.tpl("mypage/changePw");
        }
        System.out.println(authentication);
        if(authentication!=null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
            mypageService.changePassword(userDetails.getUsername(), requestChangePw.getNewpwd());
        } else {
            return utils.tpl("mypage/changePw");
        }
        return "/front/home";
    }



    // 이메일 수정
    @GetMapping("/changeMail")
    public String changeMailForm(@ModelAttribute RequestJoin requestJoin, Model model) {


        // 이메일 인증 여부 false로 초기화
        model.addAttribute("EmailAuthVerified", false);

        return utils.tpl("mypage/changeMail");
    }

    @PostMapping("/changeMail")
    public String changeMailPs(@Valid RequestJoin form, Errors errors, Model model, SessionStatus sessionStatus) {

        // EmailAuthVerified 세션값 비우기 */
        sessionStatus.setComplete();

        // 이메일 수정 후 리다이렉트
        return "redirect:" + utils.tpl("/mypage/profile");

    }


    /* 타입리프 페이지전화확인은위해 url매핑처림 s */
    /* 이지은 1월 12일 */
    // 지표수정
    @GetMapping("changeIndicator")
    public String changeIndicator() {
        /* 보조지표수정페이지로 전환 */
        return utils.tpl("mypage/changeIndicator");
    }

    /**
     * 내 활동
     *
     * @return
     */
    @GetMapping("/myBoard")
    public String myBoard() {

        return utils.tpl("mypage/myBoard");
    }

    /**
     * 팔로우
     *
     * @return
     */
    @GetMapping("/follow")
    public String follow() {

        return utils.tpl("mypage/follow");
    }
    /* 타입리프 페이지전화확인은위해 url매핑처림 e */

    // 회원 탈퇴
    @GetMapping("/deleteMember")
    public String deleteMemberForm() {
        return utils.tpl("mypage/deleteMember");
    }

    /* 잠시 닫아 둘게요 - 이다은 1월 13일
    @PostMapping("/deleteMember")
    public ModelAndView deleteMember(@ModelAttribute("currentUser") Member member,
                                     HttpSession session, ModelAndView mv) {

        // 전달받은 객체에 비밀번호 있을 때
        if (member.getPassword() != null) {
            // 암호화된 비밀번호
            String encPwd = ((Member) session.getAttribute("loginUser")).getPassword();

            if (bcryptPasswordEncoder.matches(member.getPassword(), encPwd)) {
                int result = Integer.parseInt(memberService.deleteMember(member.getUserId()));

                if (result > 0) {
                    session.removeAttribute("loginUser");
                    mv.setViewName(utils.tpl("redirect:/mypage/profile"));
                } else {
                    mv.addObject("errorMsg", "회원탈퇴 실패").setViewName(utils.tpl("mypage/deleteMember"));
                }

            } else {
                session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
                mv.setViewName(utils.tpl("mypage/deleteMember"));
            }

        }
        return mv;
    }
    */
}