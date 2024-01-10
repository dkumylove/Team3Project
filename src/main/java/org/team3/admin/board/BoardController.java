package org.team3.admin.board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.commons.ExceptionProcessor;

import java.util.ArrayList;
import java.util.List;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
public class BoardController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode() {  // 주 메뉴 코드

        return "board";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {  // 서브메뉴

        return Menu.getMenus("board");
    }

    /**
     * 게시판 목록
     * @return
     */
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);
        return "admin/board/list";
    }
    /**
     * 게시판 등록
     */
    @GetMapping("/add")
    public String add(Model model) {
        commonProcess("add", model);
        return "admin/board/add";
    }

    /**
     * 게시판 등록/ 수정 처리
     * @return
     */
    @PostMapping("/save")
    public String save() {

        return "redirect:/admin/board";
    }

    /**
     * 게시글 관리
     *
     * @return
     */
    @GetMapping("/posts")
    public String posts(Model model) {
        commonProcess("posts", model);
        return "admin/board/posts";
    }

    /**
     * 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {

        String pageTitle = "게시판 목록";
        mode = StringUtils.hasText(mode) ? mode : "list";


        if (mode.equals("add")) {
            pageTitle = "게시판 등록";
        } else if (mode.equals("edit")) {
            pageTitle = "게시판 수정";
        } else if (mode.equals("posts")) {
            pageTitle = "게시글 관리";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();
        if(mode.equals("add") || mode.equals("edit")) {  //게시판 등록 또는 수정
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");

            addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}

