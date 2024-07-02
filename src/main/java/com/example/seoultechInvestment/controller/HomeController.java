package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String authEmail() {
//        model.addAttribute("athEmailForm", new AthDTO());
        return "authEmail";
    }

    @GetMapping("/login")
    public String authEmail(Model model) {
        model.addAttribute("memberForm", new SignInDTO());
        return "signIn";
    }

    @GetMapping("/stInvestmentHome")
    public String stInvestmentHome(Model model) {
        return "stInvestmentHome";
    }
}
