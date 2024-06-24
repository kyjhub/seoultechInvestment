package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

//관리자 계정으로 로그인하면 다른 화면으로 들어가도록
//일반회원과 관리자가 로그인하면 넘어가는 페이지가 다른 걸로
@Controller
public class SignInController {
    @PostMapping("/login")
    public String login(@Valid SignInDTO signInDTO, BindingResult bindingResult) {

        return "";
    }
}
