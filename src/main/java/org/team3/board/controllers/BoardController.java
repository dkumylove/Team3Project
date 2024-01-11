package org.team3.board.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.team3.board.repositories.BoardDataRepository;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;


@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController implements ExceptionProcessor {

    private final Utils utils;
    private final BoardDataRepository boardDataRepository;

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




    /*@ResponseBody
    @GetMapping("/test")
    public void test() {
        BoardData data = boardDataRepository.findById(1L).orElse(null);
        data.setSubject("(수정)제목");
        boardDataRepository.flush();

        *//*
        BoardData data = new BoardData();
        data.setSubject("제목");
        data.setContent("내용");
        boardDataRepository.saveAndFlush(data);
         *//*
    }*/

    /*@ResponseBody
    @GetMapping("/test2")
    @PreAuthorize("hasAuthority('ADMIN')")
    //@Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
    public void test2() {
        System.out.println("test2 ================");
    }*/
}
