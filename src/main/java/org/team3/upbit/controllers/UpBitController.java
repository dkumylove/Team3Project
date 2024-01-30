package org.team3.upbit.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.Utils;
import org.team3.upbit.entities.UpBitTicker;
import org.team3.upbit.service.UpBitService;
import org.team3.upbit.service.UpBitTickerSearch;

import java.util.List;

@Controller
@RequestMapping("/upbit")
@RequiredArgsConstructor
public class UpBitController {

    private final Utils utils;
    private final UpBitService upBitService;

    @GetMapping("/main")
    public String index(@ModelAttribute UpBitTickerSearch search, Model model) {
//        List<String> addCommonScript = new ArrayList<>();
//        addCommonScript.add("upbit");
//
//        model.addAttribute("addCommonScript",addCommonScript);
//        List<UpBitTicker> date = upBitService.getList(search);
//
//        model.addAttribute("items", date);
        model.addAttribute("addCommonScript", new String[] {"upbit2"});

        return utils.tpl("upbit/main");
    }
}