package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberService;
import org.team3.member.validator.currentUser;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage/{uesrId}")
public class MypageUpdateController {

    private final JoinService joinService;

    private MemberService memberService;

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    // 마이페이지 포워딩
    @RequestMapping()
    public String myPage() {

        return "";
    }

    // 회원정보 가져오기
    @GetMapping()
    public String mypageUpdateForm(@currentUser Member member, Model model) {
        model.addAttribute(member);
        model.addAttribute(new Profile(member));
        return "";
    }

    // 수정 확인 여부 후 포워딩
    @PostMapping()
    public String updateProfile(@currentUser Member member, @Valid @ModelAttribute Profile profile, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute(member);
            return "";
        }

        joinService.updateProfile(member, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");
        return "redirect:" + "";
    }

    // 비밀번호 수정 시 현재 비밀번호 확인
    @RequestMapping()
    public String passwordCheck(String oldPassword, HttpSession session) {

        String newPassword = ((Member)session.getAttribute("profile")).getPassword();

        return "";
    }

    // 비밀번호 변경
    @RequestMapping()
    public String updateMemberPassword(@ModelAttribute  Member member, HttpSession session, String newPassword) {
        //새 비밀번호 암호화
        newPassword = bcryptPasswordEncoder.encode(member.getPassword());

        member.setPassword(newPassword);

        int result = Integer.parseInt(memberService.updateMemberPassword(member));
        //loginUser의 비밀번호 바꿔주기
        if (result > 0) {
            ((Member)session.getAttribute("loginUser")).setPassword(newPassword);
        }
        return "";
    }

    // 회원탈퇴 포워딩
    @RequestMapping()
    public String deleteView() {
        return "";
    }

    // 회원탈퇴
    @RequestMapping()
    public ModelAndView deleteMember(Member member, HttpSession session, ModelAndView mv) {

        // 전달받은 객체에 비밀번호 있을 때
        if(member.getPassword() != null) {
            // 암호화된 비밀번호
            String encPwd = ((Member)session.getAttribute("loginUser")).getPassword();

            if(bcryptPasswordEncoder.matches(member.getPassword(), encPwd)){
                int result = Integer.parseInt(memberService.deleteMember(member.getUserId()));

                if(result > 0) {
                    session.removeAttribute("loginUser");
                    mv.setViewName("member/deleteMemberSuccess");
                } else {
                    mv.addObject("errorMsg", "회원탈퇴 실패").setViewName("common/errorPage");
                }

            } else {
                session.setAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
                mv.setViewName("redirect:");
            }

        }
        return mv;
    }
}
