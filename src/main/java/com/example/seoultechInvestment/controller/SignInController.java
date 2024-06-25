package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//관리자 계정으로 로그인하면 다른 화면으로 들어가도록
//일반회원과 관리자가 로그인하면 넘어가는 페이지가 다른 걸로
@Controller
@RequiredArgsConstructor
public class SignInController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Valid SignInDTO signInDTO, BindingResult bindingResult, HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            return "redirect:/login";
        }

        Member loginMember = loginService.login(signInDTO);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("loginMember", loginMember);

        return "stInvestmentHome";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
