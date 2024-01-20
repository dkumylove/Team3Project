package org.team3.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.admin.member.controllers.MemberSearchOptions;
import org.team3.board.controllers.BoardDataSearch;
import org.team3.board.entities.BoardData;
import org.team3.board.service.SaveBoardDataService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Utils;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;
import org.team3.member.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MemberService memberService;
    private final MemberInfoService memberInfoService;
    private final ChangePasswordService mypageService;
    private final MemberUtil memberUtil;
    private final ChangeEmailService changeEmail;
    private final ChangePwValidator changePwValidator;
    private final ChangeEmailValidator changeEmailValidator;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final HttpServletRequest request;
    private final MemberRepository memberRepository;
    private final MemberDeleteService memberDeleteService;
    private final SaveBoardDataService saveBoardDataService;

    // 마이페이지
    @GetMapping
    public String mypage(@ModelAttribute MemberSearchOptions search, Model model) {

        ListData<Member> data = memberInfoService.getList(search);
        System.out.println(data.getItems());

        model.addAttribute("memberList", data.getItems()); // 목록


        return utils.tpl("mypage/profile");
    }

    @GetMapping("/profile")
    public String profileForm(Model model) {
        commonProcess("profile", model);

        return utils.tpl("mypage/profile");
    }

    // 새로 만들게요 - 이다은 1월 15일

//    private void commonProcess(String mode, Model model) {
//        mode = Objects.requireNonNullElse(mode, "list");
//        String pageTitle = "회원 목록";
//
//
//        model.addAttribute("subMenuCode", mode);
//    }

    // 하단으로 내릴꼐요 - 이지은 1월 16일
//    private void commonProcess(String mode, Model model){
//        mode = StringUtils.hasText(mode) ? mode : "profile";
//        String pageTitle = Utils.getMessage("회원가입", "commons");
//
//        List<String> addCommonScript = new ArrayList<>(); // 공통 자바스크립트
//        List<String> addScript = new ArrayList<>(); // 프론트 자바스크립트
//        List<String> addCss = new ArrayList<>(); // css추가
//
//        if(mode.equals("changeEmail")){
//            addScript.add("mypage/changeEmail");
//        }
//        model.addAttribute("addScript", addScript);
//    }


    @PostMapping("/profile")
    public String profile(@ModelAttribute Member member) {
        memberService.save(member);
        return utils.tpl("mypage/profile");
    }

    // 닉네임 수정
    @GetMapping("/changeNickname")
    public String changeNicknameForm(Model model) {
        commonProcess("changeNickname", model);

        return utils.tpl("mypage/changeNickname");
    }

    // 자바 스크립트로 처리 - 이다은 : 1월 18일
//    @PostMapping("/changeNickname.js")
//    public String changeNickname.js(@ModelAttribute Member member) {
//
//
//        memberService.updateMemberNickname(member.getNickName());
//        return utils.tpl("mypage/profile");
//    }

    // 비밀번호 수정
    @GetMapping("/changePw")
    public String changePwForm(@ModelAttribute RequestChangePw requestChangePw, Model model) {
        commonProcess("changePw", model);
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

    // 자바스크립트로 처리
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/changePw")
//    public String changePw(@Valid RequestChangePw requestChangePw, Errors errors, Model model) {
//
//        commonProcess("changePw", model);
//
//        // 현재 사용자 정보
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        changePwValidator.validate(requestChangePw, errors);
//        System.out.println(authentication);
//
//        if(errors.hasErrors()){
//            System.out.println(authentication);
//            return utils.tpl("mypage/changePw");
//        }
//
//        // System.out.println(authentication); // 현재 사용자정보
//
//        if(authentication!=null && authentication.isAuthenticated()) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            System.out.println(userDetails.getUsername()); // 현재 사용자 정보
//            mypageService.changePassword(userDetails.getUsername(), requestChangePw.getNewpwd());
//            return "redirect:/mypage/profile";
//        } else {
//            return utils.tpl("mypage/changePw");
//        }
//    }

    /**
     * 강제형변환 부분에서 에러 발생으로 인한 수정
     * 1월 19일 이지은
     * @param model
     */
    @ModelAttribute
    public void modeladd(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails memberInfo = (UserDetails) authentication.getPrincipal();
        model.addAttribute("email", memberInfo.getUsername());

        Member member = memberRepository.findByUserId(memberInfo.getUsername()).orElse(null);
        model.addAttribute("member", member);
        System.out.println("$$$$$$$$$$$$$$$"+member);
        // Member member = memberUtil.getMember();
//        if (member != null) {
//            model.addAttribute("nickName", member.getNickName());
//        }
    }


    // 이메일 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/changeEmail")
    public String changeMailForm(@ModelAttribute RequestChangeEmail requestChangeEmail, Model model) {

        commonProcess("changeEmail", model);
        HttpSession session = request.getSession();
        session.removeAttribute("EmailAuthVerified");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
//        model.addAttribute("email", memberInfo.getEmail());

        // sessionStatus.
        // 이메일 인증 여부 false로 초기화
        // model.addAttribute("EmailAuthVerified", false);
        // sessionStatus.setComplete();
        return utils.tpl("mypage/changeEmail");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/changeEmail")
    public String changeMailPs(@Valid RequestChangeEmail requestChangeEmail, Errors errors, Model model) {

        changeEmailValidator.validate(requestChangeEmail, errors);

        if(errors.hasErrors()){
            model.addAttribute("EmailAuthVerified", false);
            return utils.tpl("mypage/changeEmail");
        }

        // sessionStatus.setComplete();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        changeEmail.changeEmail(((MemberInfo) authentication.getPrincipal()).getEmail(), requestChangeEmail.getNewEmail());
        System.out.println(((MemberInfo) authentication.getPrincipal()).getEmail() +" " + ((MemberInfo) authentication.getPrincipal()).getPassword());
        // authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials()));
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication,authentication.getName()));

        // System.out.println("**********"+authentication.getPrincipal());
        HttpSession session = request.getSession();
        session.removeAttribute("EmailAuthVerified");

        // 이메일 수정 후 리다이렉트
        return "redirect:/mypage/profile";
    }

    /**
     * @description 새로운 인증 생성
     * @param currentAuth 현재 auth 정보
     * @param username	현재 사용자 Id
     * @return Authentication
     * @author Armton
     */
    protected Authentication createNewAuthentication(Authentication currentAuth, String username) {
        UserDetails newPrincipal = memberInfoService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(newPrincipal, currentAuth.getCredentials(), newPrincipal.getAuthorities());
        newAuth.setDetails(currentAuth.getDetails());
        return newAuth;
    }


    /* 타입리프 페이지전화확인은위해 url매핑처림 s */
    /* 이지은 1월 12일 */
    // 지표수정
    @GetMapping("changeIndicator")
    public String changeIndicator(Model model) {
        commonProcess("changeIndicator", model);
        /* 보조지표수정페이지로 전환 */
        return utils.tpl("mypage/changeIndicator");
    }

    /**
     * 내 활동
     * 1월 18일 이지은
     *
     * @return
     */
    @GetMapping("/myBoard")
    public String myBoard(@ModelAttribute BoardDataSearch search, Model model) {
        commonProcess("myBoard", model);

        String mode = "myBoard";

        if (mode.equals("save_post")) {
            ListData<BoardData> data = saveBoardDataService.getList(search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }

        return utils.tpl("mypage/myBoard");
    }

    /**
     * 팔로우
     * 1월 16일 이지은
     * @return
     */
    @GetMapping("/follow")
    public String follow(Model model) {
        commonProcess("follow", model);
        return utils.tpl("mypage/follow");
    }

    @GetMapping("/content/{tab}")
    public String content(@PathVariable("tab") String tab) {

        return utils.tpl("mypage/content/" + tab);
    }


    /* 타입리프 페이지전화확인은위해 url매핑처림 e */

    // 회원 탈퇴
    @GetMapping("/deleteMember")
    public String deleteMemberForm(Model model) {
        commonProcess("delete",  model);
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
    @DeleteMapping("/deleteMember")
    public String deactivateMember(Long seq) {
        memberDeleteService.deactivateMember(seq);
        ResponseEntity.ok("회원탈퇴 완료");

        return "redirect:/main/index";
    }

    /**
     * 공통기능
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {

        String pageTitle = Utils.getMessage("profile", "commons");  // 마이페이지 기본 파이틀
        mode = StringUtils.hasText(mode) ? mode : "profile"; // 없으면 기본값 profile


        List<String> addCommonScript = new ArrayList<>();    // 공통 자바스크립트
        List<String> addCommonCss = new ArrayList<>();    // 공통 CSS
        List<String> addCss = new ArrayList<>();       // 프론트 CSS
        List<String> addScript = new ArrayList<>();    // 프론트 자바스크립트

         addCommonScript.add("fileManager");

        if (mode.equals("changeEmail")) {  // 이메일 수정
            addScript.add("mypage/changeEmail");
            pageTitle = Utils.getMessage("changeEmail", "commons");

        } else if(mode.equals("changePw")){
            pageTitle = Utils.getMessage("changePw", "commons");
            addScript.add("mypage/changePw");
        } else if(mode.equals("changeNickname")){
            pageTitle = Utils.getMessage("changeNickname", "commons");
            addScript.add("mypage/changeNickname");
        } else if (mode.equals("myBoard")) {  // 내활동
            pageTitle = Utils.getMessage("myBoard", "commons");
            addScript.add("board/common");
            addScript.add("mypage/save_post");
        } else if (mode.equals("follow")) { // 팔로우
            pageTitle = Utils.getMessage("follow", "commons");

        }

        if (mode.equals("follow") || mode.equals("myBoard")) {
            addCommonCss.add("tab");
            addCommonScript.add("tab");
        } else if (mode.equals("profile")) {
            addScript.add("mypage/profile");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addCommonCss", addCommonCss);
    }
}