package org.team3.board.controllers.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.team3.board.entities.CommentData;
import org.team3.board.service.BoardAuthService;
import org.team3.board.service.comment.CommentInfoService;
import org.team3.board.service.comment.CommentSaveService;
import org.team3.commons.ExceptionRestProcessor;
import org.team3.commons.rests.JSONData;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class ApiCommentController implements ExceptionRestProcessor {

    private final CommentInfoService commentInfoService;
    private final CommentSaveService commentSaveService;
    private final BoardAuthService boardAuthService;

    @GetMapping("/{seq}")
    public JSONData<CommentData> getComment(@PathVariable("seq") Long seq) {

        CommentData data = commentInfoService.get(seq);

        return new JSONData<>(data);
    }

    @PatchMapping
    public JSONData<Object> editComment(RequestComment form) {

        boardAuthService.check("comment_update", form.getSeq());

        form.setMode("edit");
        commentSaveService.save(form);

        return new JSONData<>();
    }

    @GetMapping("/auth_check")
    public JSONData<Object> authCheck(@RequestParam("seq") Long seq,
                                      @RequestParam("guestPw") String guestPw) {

        boardAuthService.check("comment_update", seq);

        return new JSONData<>();
    }
}
