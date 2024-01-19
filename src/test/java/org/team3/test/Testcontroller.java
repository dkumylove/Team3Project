package org.team3.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.ExceptionProcessor;
import org.team3.commons.Utils;

@Controller
@RequestMapping("/test")

public class Testcontroller implements ExceptionProcessor {

    //private final Utils utils;

//    @GetMapping("/popup")
//    public String popupTest() {
//
//        //return utils.tpl("test/popup");
//    }
}