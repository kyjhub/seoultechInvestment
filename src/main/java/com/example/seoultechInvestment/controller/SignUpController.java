package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthNumDTO;
import com.example.seoultechInvestment.DTO.EmailDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.MailService;
import com.example.seoultechInvestment.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;
    private final MailService mailService;
    @PostMapping("/signUp")//회원가입
    public String join(Model model, @Valid MemberDTO memberDTO,
                       BindingResult bindingResult) {//errors -> bindingResult로 교체
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            return "redirect:/signUp";
        }
        //멤버 등록
        if (memberDTO.getStGalleryNickname() != null) {
            Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                    Department(memberDTO.getDepartment()).stGalleryNickname(memberDTO.getStGalleryNickname()).
                    email(memberDTO.getEmail()).build();
            memberService.enroll(newMember);
        } else {
            Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                    Department(memberDTO.getDepartment()).email(memberDTO.getEmail()).build();
            memberService.enroll(newMember);
        }

        return "signIn";
    }

    //쿠키를 servletResponse에 담아서 보낼려면 @ResponseBody가 필요한지? 필요없는듯
    @PostMapping("/email")
    public String getEmail(@Valid EmailDTO emailDTO, HttpServletResponse response) {
        System.out.println("보낼 이메일 : " + emailDTO.getEmail());
        String emailUUid = UUID.randomUUID().toString();
        //쿠키 생성 후 저장
        Cookie emailCookie = new Cookie("EMAIL_COOKIE_NAME", emailUUid);
        response.addCookie(emailCookie);
        //joinEmail 메소드 안에 이메일 전송 코드 있음.
        log.info(mailService.joinEmail(emailDTO.getEmail(), emailUUid));
        return "redirect:/signUp";
    }

    @PostMapping("/email/ath")   // 인증번호 오면 검증하는 단계
    public String authenticationEmail( @Valid AthNumDTO athNumDTO, HttpServletRequest request) {
        Cookie emailCookie = Arrays.stream(request.getCookies()).
                filter(c -> c.getName().equals("EMAIL_COOKIE_NAME")).
                findAny().orElse(null);
        String emailUUid = emailCookie.getValue(); //getvalue가 nullPointerException 오류 날 수 있음
        boolean checkAthNum = mailService.checkAthNum(emailUUid, athNumDTO.getAthNum());
        if (checkAthNum) {
            return "forward:/signUp";  //인증완료, forward해서 회원가입에서 입력중이던 회원정보들 유지
        } else {
            throw new NullPointerException("이메일 인증에서 에러 발생!");
        }
    }
}
