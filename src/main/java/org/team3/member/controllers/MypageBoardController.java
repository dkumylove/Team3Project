//package org.team3.member.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.team3.board.controllers.BoardDataSearch;
//import org.team3.board.entities.BoardData;
//import org.team3.board.service.SaveBoardDataService;
//import org.team3.commons.ExceptionProcessor;
//import org.team3.commons.ListData;
//import org.team3.commons.Utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequiredArgsConstructor
//public class MypageBoardController implements ExceptionProcessor {
//
//    private final SaveBoardDataService saveBoardDataService;
//    private final Utils utils;
//
//
////    @GetMapping // 마이페이지 메인
////    public String index(Model model) {
////        commonProcess("main", model);
////
////        return utils.tpl("mypage/index");
////    }
//
//    /**
//     * 찜 게시글 목록
//     *
//     * @param search
//     * @param model
//     * @return
//     */
//    @GetMapping("/save_post")
//    public String savePost(@ModelAttribute BoardDataSearch search, Model model) {
//        commonProcess("save_post", model);
//
//        ListData<BoardData> data = saveBoardDataService.getList(search);
//
//        model.addAttribute("items", data.getItems());
//        model.addAttribute("pagination", data.getPagination());
//
//        return utils.tpl("mypage/content/save_post");
//    }
//
//    private void commonProcess(String mode, Model model) {
//        mode = StringUtils.hasText(mode) ? mode : "myboard";
//        String pageTitle = Utils.getMessage("myboard", "commons");
//
//        List<String> addCss = new ArrayList<>();
//        List<String> addScript = new ArrayList<>();
//
//        if (mode.equals("save_post")) { // 찜한 게시글 페이지
//            pageTitle = Utils.getMessage("찜_게시글", "commons");
//
//            addScript.add("board/common");
//            addScript.add("mypage/save_post");
//        }
//
//        model.addAttribute("pageTitle", pageTitle);
//        model.addAttribute("addCss", addCss);
//        model.addAttribute("addScript", addScript);
//    }
//
//}