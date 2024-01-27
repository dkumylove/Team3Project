package org.team3.admin.option.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.board.controllers.BoardConfigValidator;
import org.team3.admin.board.controllers.BoardSearch;
import org.team3.admin.member.controllers.RequestMemberConfig;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.admin.option.entities.Options;
import org.team3.admin.option.service.OptionConfigInfoService;
import org.team3.admin.option.service.OptionConfigSaveService;
import org.team3.board.entities.Board;
import org.team3.board.service.config.BoardConfigDeleteService;
import org.team3.board.service.config.BoardConfigInfoService;
import org.team3.board.service.config.BoardConfigSaveService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.commons.Utils;

import java.util.ArrayList;
import java.util.List;


@Controller("adminOptionController")
@RequestMapping("/admin/option")
@RequiredArgsConstructor
public class OptionController implements ExceptionProcessor {

    private final OptionConfigSaveService optionConfigSaveService;
    private final OptionConfigInfoService optionConfigInfoService;

    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "option";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return Menu.getMenus("option");
    }



    /**
     * 보조지표 리스트
     * @param model
     * @return
     */
    @GetMapping
    public String list(@ModelAttribute OptionSearch optionSearch, Model model){
        commonProcess("list", model);
        ListData<Options> data = optionConfigInfoService.getList(optionSearch, true);
        List<Options> option = data.getItems();
        Pagination pagination = data.getPagination();

        model.addAttribute("optionList", option);
        model.addAttribute("pagination", pagination); // 페이징
        return "admin/option/list";
    }

    /**
     * 보조지표 등록
     *
     * @return
     */
    @GetMapping("/add")
    public String save(@ModelAttribute RequestOptionConfig config, Model model) {
        commonProcess("add", model);

        return "admin/option/add";
    }

    @PostMapping("/save")
    public String add(@ModelAttribute RequestOptionConfig config, Model model) {
        commonProcess("add", model);
        optionConfigSaveService.save(config.getOptions(), config.isActive());
        return "redirect:/admin/option";
    }


    /**
     * 공통 처리
     *
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "보조지표_목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if(mode.equals("add")) {
            pageTitle = "보조지표 등록";
        } else if (mode.equals("eidt")) {
            pageTitle = "보조지표 수정";
        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("add") || mode.equals("edit")) { // 게시판 등록 또는 수정
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");
            // addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }
}
