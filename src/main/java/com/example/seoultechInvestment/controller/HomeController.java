package com.example.seoultechInvestment.controller;

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

    @GetMapping("/home")
    public String login(Model model) {
        model.addAttribute("memberForm", new MemberDTO());
        return "signUp";
    }
}
