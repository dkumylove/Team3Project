package org.team3.admin.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.admin.menus.Menu;
import org.team3.admin.menus.MenuDetail;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.member.config.MemberConfigDeleteService;
import org.team3.member.config.MemberConfigSaveService;
import org.team3.member.config.MemberConfigValidator;
import org.team3.member.controllers.MemberSearch;
import org.team3.member.entities.Member;
import org.team3.member.service.MemberInfoService;
import org.team3.member.config.MemberConfigInfoService;


import java.util.ArrayList;
import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {

    //    private final MemberRepository repository;
    private final MemberConfigInfoService configInfoService;
    private final MemberConfigSaveService configSaveService;
    private final MemberConfigDeleteService configDeleteService;
    private final MemberConfigValidator validator;


//    @ModelAttribute("memberList")
//    public List<Member> getMemberList(){
//        return repository.findAll();
//    }

    // 메뉴는 공통으로 쓰는 부분임


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
    public String list(@ModelAttribute MemberSearch search, Model model) {
        commonProcess("list", model);

        ListData<Member> data = configInfoService.getList(search, true);
        List<Member> items = data.getItems();
        Pagination pagination = data.getPagination();


        System.out.println(data.getItems());

        model.addAttribute("MemberList", items); // 목록
        model.addAttribute("pagination", pagination); // 페이징

        return "admin/member/list";
    }

    /**
     * 게시판 등록/수정 처리
     *
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestMemberConfig config, Errors errors, Model model) {
        String mode = config.getMode();

        commonProcess(mode, model);
        validator.validate(config, errors);

        if (errors.hasErrors()) {
            errors.getAllErrors().stream().forEach(System.out::println);
            return "admin/member/" + mode;
        }

        configSaveService.save(config);

        return "redirect:/admin/member";
    }

    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, Model model) {
        commonProcess("edit", model);

        RequestMemberConfig requestMemberConfig = configInfoService.getForm(userId);
        model.addAttribute("requestMemberConfig", requestMemberConfig);

        return "admin/member/edit";
    }
    /**
     * 회원 등록
     *
     * @return
     */
    @GetMapping("/add")
    public String add(@ModelAttribute RequestMemberConfig config, Model model) {
        commonProcess("add", model);

        return "admin/member/add";
    }
    private void commonProcess(String mode, Model model) {
//        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "회원목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if(mode.equals("add")) {
            pageTitle = "회원 등록";

        } else if (mode.equals("edit")) {
            pageTitle = "회원 수정";

        }

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if(mode.equals("add") || mode.equals("edit")) {
            addCommonScript.add("fileManager");
            addScript.add("member/form");
            // addScript.add("board/form");
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("subMenuCode", mode);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
    }


    @DeleteMapping
    public String deleteList(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("list", model);

        configDeleteService.deleteList(chks);

        model.addAttribute("script", "parent.location.reload();");

        return "common/_execute_script";
    }


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

}