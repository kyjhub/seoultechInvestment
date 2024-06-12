package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AthDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.MailService;
import com.example.seoultechInvestment.service.MemberService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SignUpController {
    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("signUp")
    public String getSignUp(Model model) {
        log.debug("GetMapping of signUp.html");
        model.addAttribute("memberForm", new MemberDTO());
        return "signUp";
    }

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
                    Department(memberDTO.getDepartment()).stGalleryNickname(memberDTO.getStGalleryNickname()).build();
            memberService.enroll(newMember);
        } else {
            Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                    Department(memberDTO.getDepartment()).build();
            memberService.enroll(newMember);
        }

        return "";
    }
    @GetMapping("/resultOfAth")
    @ResponseBody
    public String finalAth(Model model) {
        log.debug("resultOfAth getMapping success");
        return "http://localhost:8080/signUp";
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
