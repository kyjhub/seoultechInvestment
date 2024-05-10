package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthNumDTO;
import com.example.seoultechInvestment.DTO.EmailDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/join")
    public String login(Model model) {
        model.addAttribute("memberForm", new MemberDTO());
        model.addAttribute("EmailForm", new EmailDTO());
        model.addAttribute("athNumForm", new AthNumDTO());
        return "signUp";
    }

    //redirect로 올 때 받아줄 매핑
    @GetMapping("/signUp")
    public String redirectPurpose(Model model) {
        model.addAttribute("memberForm", new MemberDTO());
        model.addAttribute("EmailForm", new EmailDTO());
        model.addAttribute("athNumForm", new AthNumDTO());
        return "signUp";
    }

    //forward로 올 때 받아줄 매핑
    //forward로 오면 이전 요청이 그대로 살아있다고했으니까 email이 들어있겠지
    //signUpController에 @PostMapping("signUp")가 이미 있어서 다른걸로함
    @PostMapping("forwardSignUp")
    public String forwardPurpose(@Valid EmailDTO emailDTO) {
        log.debug(emailDTO.toString());
        return "signUp";
    }
}
