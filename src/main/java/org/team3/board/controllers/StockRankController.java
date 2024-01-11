package org.team3.board.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;

@Controller
@RequestMapping("rank")
@RequiredArgsConstructor
public class StockRankController implements ExceptionProcessor {

    private final Utils utils;

    @GetMapping //보조지표랭킹 페이지로 이동
    public String rank () {

        return utils.tpl("board/stockRank");
    }
}
