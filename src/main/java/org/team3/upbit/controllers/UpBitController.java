//package org.team3.upbit.controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.team3.commons.Utils;
//
//@Controller
//@RequestMapping("/upbit")
//@RequiredArgsConstructor
//public class UpBitController {
//
//    private final Utils utils;
//
//    @GetMapping("/main")
//    public String index(Model model) {
////        List<String> addCommonScript = new ArrayList<>();
////        addCommonScript.add("upbit");
////
////        model.addAttribute("addCommonScript",addCommonScript);
//
//        model.addAttribute("addCommonScript", new String[] {"upbit"});
//
//        return utils.tpl("upbit/main");
//    }
//}