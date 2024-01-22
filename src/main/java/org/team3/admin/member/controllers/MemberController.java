package org.team3.admin.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.admin.board.controllers.RequestBoardConfig;
import org.team3.admin.config.service.ConfigInfoService;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.member.config.MemberConfigDeleteService;
import org.team3.member.config.MemberConfigSaveService;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Member;
import org.team3.member.service.MemberConfigInfoService;
import org.team3.member.service.MemberInfoService;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    //    private final MemberRepository repository;
    private final MemberInfoService memberInfoService;
    private final MemberConfigInfoService configInfoService;
    private final MemberConfigSaveService configSaveService;
    private final MemberConfigDeleteService configDeleteService;


//    @ModelAttribute("memberList")
//    public List<Member> getMemberList(){
//        return repository.findAll();
//    }

    // 메뉴는 공통으로 쓰는 부분임

    /**
     * 게시판 목록 - 수정
     *
     * @param chks
     * @return
     */
    @PatchMapping
    public String editList(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("list", model);
        System.out.println("chks"+chks);
        configSaveService.saveList(chks);

        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";
    }
//
//    @DeleteMapping
//    public String deleteList(@RequestParam("chk") List<Integer> chks, Model model) {
//        commonProcess("list", model);
//
//        configDeleteService.deleteList(chks);
//
//        model.addAttribute("script", "parent.location.reload();");
//
//        return "common/_execute_script";
//    }
//

    /**
     * subMenus 라는 속성값이 있으면 값이 모든 컨트롤러에서 공유가 됨
     */
    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("member");
    }

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }

    //    @GetMapping
//    public String list(Model model) {
//        // 컨트롤러어드바이스를 이용하면 됨
//        model.addAttribute("subMenuCode", "list");
//        return "admin/member/list";
//    }
    @GetMapping
    public String list(@ModelAttribute MemberSearchOptions search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = memberInfoService.getList(search);

        System.out.println(data.getItems());

        model.addAttribute("MemberList", data.getItems()); // 목록
        model.addAttribute("pagination", data.getPagination()); // 페이징

        return "admin/member/list";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, Model model) {
        commonProcess("edit", model);

        MemberSearchOptions form = configInfoService.getForm(userId);
        model.addAttribute("requestMemberConfig", form);
        configInfoService.getForm(userId);
        model.addAttribute("requestJoin", form);

        return "admin/member/edit";
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }

}