package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.EmailDTO;
import com.example.seoultechInvestment.DTO.MemberDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            return "redirect:/login";
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

        return "";
    }

    @PostMapping("/email")//이메일 입력하고 인증버튼 누르면 여기에서 인증번호 보내주면 팝업창에서 인증
    public void getEmail(@RequestBody @Valid EmailDTO emailDTO) {
        System.out.println("보낼 이메일 : " + emailDTO.getEmail());
        mailService.joinEmail(emailDTO.getEmail());
    }

    @PostMapping("/athEmail")   // 인증번호 오면 검증하는 단계
    public String authenticationEmail() {
        return "forward:/signUp.html";
    }
}
