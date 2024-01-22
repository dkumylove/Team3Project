package org.team3.board.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.team3.board.controllers.comment.RequestComment;
import org.team3.board.entities.Board;
import org.team3.board.entities.BoardData;
import org.team3.board.repositories.BoardDataRepository;
import org.team3.board.service.*;
import org.team3.board.service.config.BoardConfigInfoService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
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
    private final BoardSaveService boardSaveService;
    private final BoardInfoService boardInfoService;
    private final BoardDeleteService boardDeleteService;
    private final BoardAuthService boardAuthService;

    private final MemberUtil memberUtil;
    private final Utils utils;
    private final BoardDataRepository boardDataRepository;

    private Board board; // 게시판 설정
    private BoardData boardData; // 게시글

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
    public String list(@PathVariable("bid") String bid,
                       @ModelAttribute BoardDataSearch search, Model model) {
        commonProcess(bid, "list", model);

        ListData<BoardData> data = boardInfoService.getList(bid, search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return utils.tpl("board/list");
    }

    /**
     * 게시글 보기
     * @param seq : 게시글 번호
     * @param model
     * @return
     */
    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq,
                       @ModelAttribute BoardDataSearch search, Model model) {
        boardInfoService.updateViewCount(seq); // 조회수 업데이트

        commonProcess(seq, "view", model);

        // 게시글 보기 하단 목록 노출 S
        if(board.isShowListBelowView()) {
            ListData<BoardData> data = boardInfoService.getList(board.getBid(), search);

            model.addAttribute("items", data.getItems());
            model.addAttribute("pagination", data.getPagination());
        }
        // 게시글 보기 하단 목록 노출 E

        // 댓글 커맨드 객체 처리 S
        RequestComment requestComment = new RequestComment();
        if (memberUtil.isLogin()) {
            requestComment.setCommenter(memberUtil.getMember().getName());
        }

        model.addAttribute("requestComment", requestComment);
        // 댓글 커맨드 객체 처리 E

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

        RequestBoard form = boardInfoService.getForm(boardData);
        model.addAttribute("requestBoard", form);

        return utils.tpl("board/update");
    }

    @GetMapping("/reply/{seq}")
    public String reply(@PathVariable("seq") Long parentSeq,
                        @ModelAttribute RequestBoard form, Model model) {
        commonProcess(parentSeq, "reply", model);

        String content = boardData.getContent();
        content = String.format("<br><br><br><br><br>===================================================<br>%s", content);

        form.setBid(board.getBid());
        form.setContent(content);
        form.setParentSeq(parentSeq);

        if (memberUtil.isLogin()) {
            form.setPoster(memberUtil.getMember().getName());
        }

        return utils.tpl("board/write");
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

        // 게시글 저장 처리
        BoardData boardData = boardSaveService.save(form);

        String redirectURL = "redirect:/board/";
        redirectURL += board.getLocationAfterWriting().equals("view") ? "view/" +
        boardData.getSeq() : "list/" + form.getBid();

        return redirectURL;
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {
        commonProcess(seq, "delete", model);

        boardDeleteService.delete(seq);

        return "redirect:/board/list/" + board.getBid();
    }

    /**
     * 비회원 글수정, 글삭제 비밀번호 확인
     *
     * @param password
     * @param model
     * @return
     */
    @PostMapping("/password")
    public String passwordCheck(@RequestParam(name="password", required = false) String password,
                                Model model) {

        boardAuthService.validate(password);

        model.addAttribute("script", "parent.location.reload();");

        return "common/_execute_script";
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

        addScript.add("board/common"); // 게시판 공통 스크립트

        addCommonScript.add("follow"); // 팔로잉, 언팔로잉

        /* 게시판 설정 처리 S */
        board = configInfoService.get(bid);

        // 접근 권한 체크
        boardAuthService.accessCheck(mode, board);

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
        } else if (mode.equals("view")) {
            // pageTitle - 글 제목 - 게시판 명
            pageTitle = String.format("%s | %s", boardData.getSubject(), board.getBName());
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
     * @param seq : 게시글 번호
     * @param mode
     * @param model
     */
    private void commonProcess(Long seq, String mode, Model model) {
        // 글 수정, 글 삭제 권한 체크
        boardAuthService.check(mode, seq);

        boardData = boardInfoService.get(seq);

        String bid = boardData.getBoard().getBid();
        commonProcess(bid, mode, model);

        model.addAttribute("boardData", boardData);
    }

    @Override
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletResponse response,
                               HttpServletRequest request, Model model) {

        if(e instanceof GuestPasswordCheckException) {

            return utils.tpl("board/password");
        }

        return ExceptionProcessor.super.errorHandler(e, response, request, model);
    }
}
