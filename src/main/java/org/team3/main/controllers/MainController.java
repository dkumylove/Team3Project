package org.team3.main.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;

@Controller
@RequiredArgsConstructor
public class MainController implements ExceptionProcessor {

    private final Utils utils;

//    @ModelAttribute("addCss")
//    public String[] addCss() {
//        return new String[]{"main/style"};
//    }

    @GetMapping("/")
    public String index() {

        return utils.tpl("main/index");
    }
}