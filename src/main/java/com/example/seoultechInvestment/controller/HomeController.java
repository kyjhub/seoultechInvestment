package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import com.example.seoultechInvestment.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final LoginService loginService;

    @GetMapping("/")
    public String home(HttpServletRequest httpServletRequest) {
        log.debug("HomeController home() method 실행");

        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
//            if (loginService.administratorLogin(session)) {
//                return "homeOfAdmin";
//            } else return "home";
            return "home";
        } else return "index";
    }
//    @GetMapping("/index")
//    public String getHome(){
//
//        return "index";
//    }

    @GetMapping("/join")
    public String authEmail() {
//        model.addAttribute("athEmailForm", new AthDTO());
        System.out.println("====== /join 컨트롤러가 성공적으로 실행되었습니다! ======");
        return "redirect:/html/authEmail.html";
    }

    @GetMapping("/login")
    public String authEmail(Model model) {
        log.debug("HomeController authEmail() 실행");

        model.addAttribute("memberForm", new SignInDTO());
        return "login";
    }

    @GetMapping("/home")
    public String stInvestmentHome() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String stInvestmentAdminHome() {
        return "homeOfAdmin";
    }

    @GetMapping("/performanceList")
    public String performanceList() {

        return "performanceList";
    }
}
