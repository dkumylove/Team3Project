package org.team3.board.controllers.comment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.team3.board.entities.CommentData;
import org.team3.board.service.BoardAuthService;
import org.team3.board.service.GuestPasswordCheckException;
import org.team3.board.service.comment.CommentDeleteService;
import org.team3.board.service.comment.CommentSaveService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;
import org.team3.commons.exceptions.AlertException;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController implements ExceptionProcessor {

    private final CommentFormValidator commentFormValidator;
    private final CommentSaveService commentSaveService;
    private final CommentDeleteService commentDeleteService;
    private final BoardAuthService boardAuthService;
    private final HttpServletRequest request;


    private final Utils utils;

    /**
     * 댓글 저장, 수정 처리
     *
     * @param model
     * @return
     */
    @PostMapping("/save")
    public String save(@Valid RequestComment form, Errors errors, Model model) {
        System.out.println("=========================");
        commentFormValidator.validate(form, errors);

        if (errors.hasErrors()) {
            FieldError error = errors.getFieldErrors().stream().findFirst().orElse(null);

            throw new AlertException(Utils.getMessage(error.getCodes()[0]), HttpStatus.BAD_REQUEST);
        }

//        String requestURI = request.getRequestURI();
//        System.out.println("requestURI" + requestURI);
        CommentData commentData = commentSaveService.save(form); // 댓글 저장, 수정
//        if(requestURI.contains("rank")){
//
//            String script = String.format("parent.location.replace('/rank/%s?comment_id=%d');", commentData.getOptions().getOptionname(), commentData.getSeq());
//            model.addAttribute("script", script);
//        } else{

            String script = String.format("parent.location.replace('/board/view/%d?comment_id=%d');", commentData.getBoardData().getSeq(), commentData.getSeq());
            model.addAttribute("script", script);
//        }




        return "common/_execute_script";
    }

    @GetMapping("/delete/{seq}")
    public String delete(@PathVariable("seq") Long seq, Model model) {

        boardAuthService.check("comment_delete", seq);

        Long boardDataSeq = commentDeleteService.delete(seq);

        return "redirect:/board/view/" + boardDataSeq;
    }

    @Override
    @ExceptionHandler(Exception.class)
    public String errorHandler(Exception e, HttpServletResponse response, HttpServletRequest request, Model model) {

        if (e instanceof GuestPasswordCheckException) {
            return utils.tpl("board/password");
        }

        return ExceptionProcessor.super.errorHandler(e, response, request, model);
    }
}
