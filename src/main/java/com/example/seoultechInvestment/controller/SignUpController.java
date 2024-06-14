package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthDTO;
import com.example.seoultechInvestment.DTO.SignUpDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.MailService;
import com.example.seoultechInvestment.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("signUp")
    public String getSignUp(Model model) {
        log.debug("GetMapping of signUp.html");
        model.addAttribute("memberForm", new SignUpDTO());
        return "signUp";
    }

    //이메일 인증 페이지를 거쳐서만 들어오도록 제한해야함.
    @PostMapping("/signUp")//회원가입
    public String join(Model model, @Valid SignUpDTO signUpDTO,
                       BindingResult bindingResult) {//errors -> bindingResult로 교체
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            return "redirect:/signUp";
        }
        //멤버 등록
        if (signUpDTO.getStGalleryNickname() != null) {
            Member newMember = Member.builder().stId(signUpDTO.getStId()).name(signUpDTO.getName()).
                    Department(signUpDTO.getDepartment()).stGalleryNickname(signUpDTO.getStGalleryNickname()).build();
            memberService.enroll(newMember);
        } else {
            Member newMember = Member.builder().stId(signUpDTO.getStId()).name(signUpDTO.getName()).
                    Department(signUpDTO.getDepartment()).build();
            memberService.enroll(newMember);
        }

        model.addAttribute("memberForm", new SignUpDTO());
        return "signIn";
    }
    @PostMapping("/email")
    @ResponseBody
    public String getEmail(@RequestBody AthDTO athDTO) {
        log.debug("email PostMapping 성공");
        String emailAcc = athDTO.getEmail();
        log.debug("입력받은 이메일 : " + emailAcc);
        return mailService.joinEmail(emailAcc);
    }
}
