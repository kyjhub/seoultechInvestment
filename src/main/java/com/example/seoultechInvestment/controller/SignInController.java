package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import com.example.seoultechInvestment.Enum.Role;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class SignInController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(@Valid SignInDTO signInDTO, BindingResult bindingResult, HttpServletRequest httpServletRequest) {

        log.debug("==login postMapping 실행==");

        if (bindingResult.hasErrors()) {
            return "redirect:/login";
        }

        Member loginMember = loginService.login(signInDTO);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        }

        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("loginMember", loginMember);

        // 관리자는 관리자용 화면으로 연결
        // spring security가 처리해주기 때문에 필요없음
        if (loginMember.getRole()== Role.ROLE_ADMIN &&
                loginMember.getStId() == 21101165L &&
                loginMember.getPassword().equals("1234")) {
            log.debug("======관리자 계정 접속 확인======");
            return "homeOfAdmin";
        }
        else return "homeOfAdmin";
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
