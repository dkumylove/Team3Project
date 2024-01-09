package org.team3.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.team3.commons.ExceptionProcessor;

@Controller("adminMainController")
@RequestMapping("/admin")
public class MainController implements ExceptionProcessor {

    @GetMapping
    public String index() {
        return "admin/main/index";
    }
}