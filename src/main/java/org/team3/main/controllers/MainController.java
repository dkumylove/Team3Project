package org.team3.main.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Utils;
import org.team3.upbit.entities.UpBitTicker;
import org.team3.upbit.service.UpBitService;
import org.team3.upbit.service.UpBitTickerSearch;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;
    private final UpBitService upBitService;

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[]{"main/style"};
    }

    @GetMapping("/")
    public String index(Model model) {

//        // UpBitTickerSearch 객체 생성
//        UpBitTickerSearch upBitTickerSearch = new UpBitTickerSearch();
//
//        // markets와 openingPrices 필드를 샘플 데이터로 채움 (실제 데이터로 대체 필요)
//        List<String> markets = Arrays.asList("BTC", "ETH", "XRP");
//        List<Long> openingPrices = Arrays.asList(50000L, 3000L, 2L);
//
//        // UpBitTickerSearch 객체를 모델에 추가
//        model.addAttribute("upBitTickerSearch", upBitTickerSearch);
//
//        // markets와 openingPrices를 별도로 모델에 추가
//        model.addAttribute("markets", markets);
//        model.addAttribute("openingPrices", openingPrices);


        model.addAttribute("addCommonScript", new String[] {"upbit"});


        return utils.tpl("main/index");
    }

}