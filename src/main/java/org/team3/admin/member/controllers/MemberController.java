package org.team3.admin.member.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.commons.ExceptionProcessor;

import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {

        return Menu.getMenus("member");
    }

    @GetMapping
    public String list(Model model) {

        model.addAttribute("subMenuCode", "list");
        return "admin/member/list";
    }
}