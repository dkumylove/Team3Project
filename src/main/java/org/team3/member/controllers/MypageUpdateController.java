package org.team3.member.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.member.entities.Member;
import org.team3.member.entities.Profile;
import org.team3.member.service.JoinService;
import org.team3.member.service.MemberService;
import org.team3.member.validator.currentUser;

import java.util.ArrayList;
import java.util.List;

@Controller("mypageUpdateController")
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageUpdateController implements ExceptionProcessor {

    private final JoinService joinService;
    private final Utils utils;

    private MemberService memberService;

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    /**
     * 타입리프 페이지전화확인은위해 url매핑처림
     * 이지은 1월 11일
     * @return
     */

//    @GetMapping("changePw")
//    public String changePassword() {
//        /* 비밀번호수정페이지로 전환 */
//        return utils.tpl("mypage/changePw");
//    }

    /* 이메일 인증페이지 때문에 필요 */
    @GetMapping("changeMail")
    public String changeEmail(@ModelAttribute RequestJoin requestJoin, Model model) {
        commonProcess("join", model);
        // model.addAttribute("pageTitle", "회원가입");

        model.addAttribute("EmailAuthVerified", false); // 이메일 인증여부 false로 초기화
        /* 이메일수정페이지로 전환 */
        return utils.tpl("mypage/changeMail");
    }

    private void commonProcess(String mode, Model model){
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");

        List<String> addCommonScript = new ArrayList<>(); // 공통 자바스크립트
        List<String> addScript = new ArrayList<>(); // 프론트 자바스크립트
        List<String> addCss = new ArrayList<>(); // cdd추가

        if(mode.equals("login")){
            pageTitle = Utils.getMessage("로그인", "commons");
        } else if(mode.equals("join")){
            addCommonScript.add("fileManager");
            addScript.add("member/form");
            addScript.add("member/join");
            addCss.add("member/join");
        } else if(mode.equals("find_pw")) { // 비밀번호 찾기
            pageTitle = Utils.getMessage("비밀번호_찾기", "commons");
        } else if(mode.equals("find_id")){
            pageTitle = Utils.getMessage("아이디_찾기", "commons");
            addScript.add("member/findId");
        }
        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        // model.addAttribute("addCss", addCss);
        // 프론트에만 필요하면 프론트로
        // 파일기능은 공통이기 때문에 common에 넣음
    }
    /* 이메일 인증 페이지 떄문에 필요 */

    @GetMapping("changeIndicator")
    public String changeIndicator() {
        /* 보조지표수정페이지로 전환 */
        return utils.tpl("mypage/changeIndicator");
    }



    // 회원정보 가져오기
    @GetMapping("/")
    public String mypageUpdateForm(@currentUser Member member, Model model) {
        model.addAttribute("member", member);
        model.addAttribute("profile", new Profile(member));
        return utils.tpl("profile");
    }

    // 수정 확인 여부 후 포워딩
    @PostMapping("/")
    public String updateProfile(@ModelAttribute("currentUser") Member member,
                                @Valid @ModelAttribute Profile profile, Errors errors,
                                Model model, RedirectAttributes attributes) {
        if (errors.hasErrors()) {
            model.addAttribute("member", member);
            return utils.tpl("profile");
        }

        joinService.updateProfile(member, profile);
        attributes.addFlashAttribute("message", "프로필을 수정했습니다.");
        return utils.tpl("redirect:/mypage/profile");
    }

    // 비밀번호 수정 시 현재 비밀번호 확인
    @PostMapping("/changePw")
    public String passwordCheck(@RequestParam("oldPassword") String oldPassword,
                                HttpSession session) {

        String newPassword = ((Member)session.getAttribute("profile")).getPassword();

        return utils.tpl("changePw");
    }

    // 비밀번호 변경
    @PostMapping("/changePwEnd")
    public String updateMemberPassword(@ModelAttribute("currentUser") Member member,
                                       HttpSession session, @RequestParam("newPassword") String newPassword) {
        //새 비밀번호 암호화
        newPassword = bcryptPasswordEncoder.encode(newPassword);

        member.setPassword(newPassword);

        int result = Integer.parseInt(memberService.updateMemberPassword(member));
        //loginUser의 비밀번호 바꿔주기
        if (result > 0) {
            ((Member)session.getAttribute("loginUser")).setPassword(newPassword);
        }
        return utils.tpl("redirect:/mypage/profile");
    }

    // 회원탈퇴 포워딩
    @GetMapping("/delete")
    public String deleteView() {
        return utils.tpl("deleteMember");
    }

    // 회원탈퇴
    @PostMapping("/delete")
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
