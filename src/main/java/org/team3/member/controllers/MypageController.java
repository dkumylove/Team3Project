package org.team3.member.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.admin.option.entities.Options;
import org.team3.board.controllers.BoardDataSearch;
import org.team3.board.entities.BoardData;
import org.team3.board.entities.CommentData;
import org.team3.board.service.BoardInfoService;
import org.team3.board.service.SaveBoardDataService;
import org.team3.board.service.comment.CommentInfoService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.RequestPaging;
import org.team3.commons.Utils;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;
import org.team3.member.repositories.MemberRepository;
import org.team3.member.service.*;
import org.team3.member.service.follow.FollowBoardService;
import org.team3.member.service.follow.FollowService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController implements ExceptionProcessor {

    private final Utils utils;
    private final MemberUtil memberUtil;

    private final MemberService memberService;
    private final MemberInfoService memberInfoService;

    private final ChangeEmailService changeEmail;
    private final ChangeEmailValidator changeEmailValidator;
    private final MemberDeleteService memberDeleteService;

    private final CommentInfoService commentInfoService;
    private final BoardInfoService boardInfoService;
    private final SaveBoardDataService saveBoardDataService;
    private final FollowBoardService followBoardService;
    private final FollowService followService;

    private final HttpServletRequest request;


    // 마이페이지
    @GetMapping
    public String mypage(@ModelAttribute MemberSearch search, Model model) {

        ListData<Member> data = memberInfoService.getList(search);
        System.out.println(data.getItems());
        
        model.addAttribute("memberList", data.getItems()); // 목록
        return utils.tpl("mypage/profile");
    }

    @GetMapping("/profile")
    public String profileForm(Model model) {
        commonProcess("profile", model);
        Member member = memberUtil.getMember();
        List<Options> options = memberInfoService.getOptions(member.getUserId());
        model.addAttribute("options", options);
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


    // 이메일 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/changeEmail")
    public String changeMailForm(@ModelAttribute RequestChangeEmail requestChangeEmail, Model model) {

        commonProcess("changeEmail", model);
        HttpSession session = request.getSession();

        session.removeAttribute("EmailAuthVerified");
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

        HttpSession session = request.getSession();
        changeEmail.changeEmail(memberUtil.getMember().getEmail(), requestChangeEmail.getNewEmail());
        memberUtil.update();

        session.removeAttribute("EmailAuthVerified");

        return "redirect:/mypage/profile";
    }


    /* 타입리프 페이지전화확인은위해 url매핑처림 s */
    /* 이지은 1월 12일 */
    // 지표수정
    @GetMapping("changeIndicator")
    public String changeIndicator(Model model) {
        commonProcess("changeIndicator", model);

        Member member = memberUtil.getMember();
        List<Options> options = memberInfoService.getOptions(member.getUserId());
        model.addAttribute("options", options);

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

        return utils.tpl("mypage/myBoard/posts");
    }

    @GetMapping("/myBoard/{tab}")
    public String myBoard(@PathVariable("tab") String tab, @ModelAttribute BoardDataSearch search, Model model) {
        commonProcess("myBoard", model);

        model.addAttribute("tab", tab);



        if (tab.equals("save_posts")) { // 찜한게시물
            ListData<BoardData> data = saveBoardDataService.getList(search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());

        } else if (tab.equals("posts")) { // 게시글
            search.setUserId(memberUtil.getMember().getUserId());
            ListData<BoardData> data = boardInfoService.getList(search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());

//            System.out.println("data = " + data);
//            System.out.println("items = " + data.getItems());

        } else if (tab.equals("comments")) { // 코멘트
//            search.setUserId(memberUtil.getMember().getUserId());
//            ListData<CommentData> data = (ListData<CommentData>) commentInfoService.getUserComments(search.getUserId());
//
//            model.addAttribute("items", data.getItems());
//            model.addAttribute("pagination", data.getPagination());

        } else if (tab.equals("latest")) { // 최근 게시글

        }

        return utils.tpl("mypage/myBoard");
    }



    /**
     * 팔로우
     * 1월 22일 수정 이지은
     * @return
     */
    @GetMapping("/follow")
    public String followList(@RequestParam(name="mode", defaultValue = "follower") String mode, RequestPaging paging, Model model) {
        commonProcess("follow", model);

        ListData<Member> data = followService.getList(mode, paging);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
        model.addAttribute("mode", mode);

        return utils.tpl("mypage/follow");
    }

    /**
     * follow 게시글
     * 유저 페이지
     *
     * @param userId
     * @param mode
     * @param search
     * @param model
     * @return
     */
    @GetMapping("/usePage/{userId}")
    public String followBoard(@PathVariable("userId") String userId,
                              @RequestParam(name="mode", defaultValue="usePage") String mode,
                              @ModelAttribute BoardDataSearch search, Model model) {
        commonProcess("usePage", model);

        // 전체 조회가 아니라면 아이디별 조회
        if (!userId.equals("all")) {
            search.setUserId(userId);
        } else {
            search.setUserId(null);
        }
        Member member = memberInfoService.getMember(userId);

        model.addAttribute("member", member);
        ListData<BoardData> data = followBoardService.getList(mode, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());
//        model.addAttribute("mode", "usePage");

        return utils.tpl("mypage/usePage");
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
            addCommonScript.add("follow");
        } else if (mode.equals("usePage")) { // 유저페이지
            pageTitle = Utils.getMessage("usePage", "commons");
            addCommonScript.add("follow");
            addScript.add("mypage/save_post");
        }

        if (mode.equals("follow") || mode.equals("myBoard") || mode.equals("usePage")) {
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