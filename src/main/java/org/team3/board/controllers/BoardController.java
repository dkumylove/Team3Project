package org.team3.board.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.board.entities.Board;
import org.team3.board.repositories.BoardDataRepository;
import org.team3.board.service.config.BoardConfigInfoService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.file.entities.FileInfo;
import org.team3.file.service.FileInfoService;
import org.team3.member.MemberUtil;
import org.team3.member.entities.Member;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {

    private final BoardConfigInfoService configInfoService;
    private final FileInfoService fileInfoService;

    private final BoardFormValidator boardFormValidator;

    private final MemberUtil memberUtil;
    private final Utils utils;
    private final BoardDataRepository boardDataRepository;

    private Board board; // 게시판 설정

    @GetMapping //자유게시판 페이지로 이동
    public String community () {

        return utils.tpl("board/community");
    }

    @GetMapping("tips") //팁과 노하우 페이지로 이동
    public String tips () {

        return utils.tpl("board/tips");
    }

    @GetMapping("asks") //팁과 노하우 페이지로 이동
    public String asks () {

        return utils.tpl("board/asks");
    }

    @GetMapping("new") //새 게시글 작성
    public String write() {

        return utils.tpl("board/new");
    }

    @PostMapping("new") //새 게시글 작성 완료
    public String writeSave() {

        //자유글 작성했으면 자유게시판 페이지로 이동
        if(true) {

            return utils.tpl("board/community");

            //팁과 노하우 글 작성했으면 팁과 노하우 페이지로 이동
        } else if (true) {

            return utils.tpl("board/tips");

            //질문글 작성했으면 질문게시판 페이지로 이동
        } else if (true) {

            return utils.tpl("board/asks");
        }
        return utils.tpl("board/community");
    }

    @GetMapping("{post_No}") //게시글 한개 조회
    public String readPost() {

        return utils.tpl("board/readPost");
    }

    /**
     * 게시판 목록
     *
     * @param bid : 게시판 아이디
     * @param model
     * @return
     */
    @GetMapping("/list/{bid}")
    public String list(@PathVariable("bid") String bid, Model model) {
        commonProcess(bid, "list", model);

        return utils.tpl("board/list");
    }

    /**
     * 게시글 보기
     * @param seq : 게시글 번호
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "view", model);

        return utils.tpl("board/view");
    }

    /**
     * 게시글 작성
     *
     * @param bid
     * @param model
     * @return
     */
    @GetMapping("/write/{bid}")
    public String write(@PathVariable("bid") String bid,
                        @ModelAttribute RequestBoard form,
                        Model model) {
        commonProcess(bid, "write", model);

        if(memberUtil.isLogin()) {
            Member member = memberUtil.getMember();
            form.setPoster(member.getName());
        }

        return utils.tpl("board/write");
    }

    /**
     * 게시글 수정
     *
     * @param seq
     * @param model
     * @return
     */
    @GetMapping("/update/{seq}")
    public String update(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "update", model);

        return utils.tpl("board/update");
    }

    @PostMapping("/save")
    public String save(@Valid RequestBoard form, Errors errors, Model model) {
        String bid = form.getBid();
        String mode = form.getMode();
        commonProcess(bid, mode, model);

        boardFormValidator.validate(form, errors);

        if(errors.hasErrors()) {
            String gid = form.getGid();

            List<FileInfo> editorFiles = fileInfoService.getList(gid, "editor");
            List<FileInfo> attachFiles = fileInfoService.getList(gid, "attach");

            form.setEditorFiles(editorFiles);
            form.setAttachFiles(attachFiles);

            return utils.tpl("board/" + mode);
        }

        Long seq = 0L; // 임시

        String redirectURL = "redirect:/board/";
        redirectURL += board.getLocationAfterWriting() == "view" ? "view/" + seq :
                "list/" + form.getBid();

        return redirectURL;
    }

    /**
     * 게시판 공통 처리 - 글목록, 글쓰기 등 게시판 ID가 있는 경우
     *
     * @param bid : 게시판 ID
     * @param mode
     * @param model
     */
    private void commonProcess(String bid, String mode, Model model) {

        mode = StringUtils.hasText(mode) ? mode : "list";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        List<String> addCommonCss = new ArrayList<>();
        List<String> addCss = new ArrayList<>();

        /* 게시판 설정 처리 S */
        board = configInfoService.get(bid);

        // 스킨별 css, js 추가
        String skin = board.getSkin();
        addCss.add("board/skin_" + skin);
        addScript.add("board/skin_" + skin);

        model.addAttribute("board", board);
        /* 게시판 설정 처리 E */

        String pageTitle = board.getBName(); // 게시판명이 기본 타이틀

        if(mode.equals("write") || mode.equals("update")) { // 쓰기 또는 수정
            if(board.isUseEditor()) { // 에디터 사용하는 경우
                addCommonScript.add("ckeditor5/ckeditor");
            }

            // 이미지 또는 파일 첨부를 사용하는 경우
            if(board.isUseUploadImage() || board.isUseUploadFile()) {
                addCommonScript.add("fileManager");
            }

            addScript.add("board/form");

            pageTitle += " ";
            pageTitle += mode.equals("update") ? Utils.getMessage("글수정", "commons")
                    : Utils.getMessage("글쓰기", "commons");
        }

        model.addAttribute("addCommonCss", addCommonCss);
        model.addAttribute("addCss", addCss);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("addScript", addScript);
        model.addAttribute("pageTitle", pageTitle);
    }

    /**
     * 게시판 공통 처리 : 게시글 보기, 게시글 수정 - 게시글 번호가 있는 경우
     *      - 게시글 조회 -> 게시판 설정
     *
     * @param seq
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model) {

    }

}
