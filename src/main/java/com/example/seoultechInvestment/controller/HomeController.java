package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
