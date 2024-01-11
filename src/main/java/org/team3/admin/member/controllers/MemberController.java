package org.team3.admin.member.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Member;
import org.team3.member.service.MemberInfoService;


import java.util.List;
import java.util.Objects;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    //    private final MemberRepository repository;
    private final MemberInfoService memberInfoService;

//
//    @ModelAttribute("memberList")
//    public List<Member> getMemberList(){
//        return repository.findAll();
//    }

    // 메뉴는 공통으로 쓰는 부분임

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
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = memberInfoService.getList(search);

        model.addAttribute("items", data.getItems()); // 목록
        model.addAttribute("pagination", data.getPagination()); // 페이징

        return "admin/member/list";
    }

    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원 목록";

        model.addAttribute("subMenuCode", mode);
        model.addAttribute("pageTitle", pageTitle);
    }
}