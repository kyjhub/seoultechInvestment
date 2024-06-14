package com.example.seoultechInvestment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/join")
    public String login() {
//        model.addAttribute("athEmailForm", new AthDTO());
        return "authEmail";
    }
}
