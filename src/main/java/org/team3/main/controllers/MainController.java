package org.team3.main.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.admin.option.controllers.OptionSearch;
import org.team3.admin.option.entities.Options;
import org.team3.admin.option.service.OptionConfigInfoService;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.ListData;
import org.team3.commons.Pagination;
import org.team3.commons.Utils;
import org.team3.upbit.entities.UpBitTicker;
import org.team3.upbit.service.UpBitService;
import org.team3.upbit.service.UpBitTickerSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;
    private final UpBitService upBitService;
    private final OptionConfigInfoService optionConfigInfoService;

    @ModelAttribute("addCss")
    public String[] addCss() {
        return new String[]{"main/style"};
    }

    @GetMapping("/")
    public String index(@ModelAttribute OptionSearch optionSearch, Model model) {

        ListData<Options> data = optionConfigInfoService.getList(optionSearch, true);
        List<Options> option = data.getItems();

        model.addAttribute("optionList", option);

        model.addAttribute("addCommonScript", new String[] {"upbit"});



        return utils.tpl("main/index");
    }

}