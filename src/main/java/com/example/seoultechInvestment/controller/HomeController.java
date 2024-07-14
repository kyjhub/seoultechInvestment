package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import com.example.seoultechInvestment.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping("/")
    public String home(HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            if (loginService.administratorLogin(session)) {
                return "homeOfAdmin";
            } else return "stInvestmentHome";
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
    public String stInvestmentHome() {
        return "stInvestmentHome";
    }

    @GetMapping("/performanceList")
    public String performanceList() {
        return "performanceList";
    }
}
