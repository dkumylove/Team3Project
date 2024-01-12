package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.member.entities.Member;
import org.team3.member.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MemberService memberService;
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    // 마이페이지
    @GetMapping("/mypage/profile")
    public String profileForm() {
        return utils.tpl("mypage/profile");
    }

    @PostMapping("/mypage/profile")
    public String profile(@ModelAttribute Member member) {
        memberService.save(member);
        return utils.tpl("mypage/profile");
    }
// 닉네임 수정
    @GetMapping("/mypage/changeNickname")
    public String changeNicknameForm() {
        return utils.tpl("mypage/changeNickname");
    }

    @PostMapping("/mypage/changeNickname")
    public String changeNickname(@ModelAttribute Member member) {
        memberService.updateMemberNickname(member.getNickName());
        return utils.tpl("mypage/profile");
    }
// 비밀번호 수정
    @GetMapping("/mypage/changePw")
    public String changePwForm() {
        return utils.tpl("mypage/changePw");
    }

    @PostMapping("/mypage/changePw")
    public String changePw(@ModelAttribute Member member, HttpSession httpSession) {
        String newPw = memberService.updateMemberPassword(member.getPassword());
        int result = Integer.parseInt(memberService.updateMemberPassword(member.getPassword()));
        //loginUser의 비밀번호 바꿔주기
        if (result > 0) {
            ((Member)httpSession.getAttribute("memberPw")).setPassword(newPw);
        }

        return utils.tpl("mypage/profile");
    }
// 이메일 수정
    @GetMapping("/mypage/changeMail")
    public String changeMailForm() {
        return utils.tpl("mypage/changeMail");
    }

    @PostMapping("/mypage/changeMail")
    public String changeMail(@ModelAttribute Member member) {
        memberService.updateMemberMail(member.getEmail());
        return utils.tpl("mypage/profile");
    }

// 지표수정

// 회원 탈퇴
    @GetMapping("/mypage/deleteMember")
    public String deleteMemberForm() {
        return utils.tpl("mypage/deleteMember");
    }

    @PostMapping("/mypage/deleteMember")
    public ModelAndView deleteMember(@ModelAttribute("currentUser") Member member,
                                     HttpSession session, ModelAndView mv) {

        // 전달받은 객체에 비밀번호 있을 때
        if(member.getPassword() != null) {
            // 암호화된 비밀번호
            String encPwd = ((Member)session.getAttribute("loginUser")).getPassword();

            if(bcryptPasswordEncoder.matches(member.getPassword(), encPwd)){
                int result = Integer.parseInt(memberService.deleteMember(member.getUserId()));

                if(result > 0) {
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
}