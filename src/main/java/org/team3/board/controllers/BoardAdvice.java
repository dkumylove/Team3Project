package org.team3.board.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.team3.board.entities.Board;
import org.team3.board.service.config.BoardConfigInfoService;

import java.util.List;

@ControllerAdvice("org.choongang")
@RequiredArgsConstructor
public class BoardAdvice {
    private final BoardConfigInfoService configInfoService;

    @ModelAttribute("boardMenus")
    public List<Board> getBoardList() {
        return configInfoService.getList();
    }
}
