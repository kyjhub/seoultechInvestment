package com.example.seoultechInvestment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            return "stInvestmentHome";
        }
        return "home";
    }

    @GetMapping("/join")
    public String login() {
//        model.addAttribute("athEmailForm", new AthDTO());
        return "authEmail";
    }
}
