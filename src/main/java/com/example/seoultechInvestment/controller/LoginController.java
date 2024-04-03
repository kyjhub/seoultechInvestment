package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.MemberDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    @PostMapping("/login")
    public String login(Model model, @Valid MemberDTO memberDTO,
                        BindingResult bindingResult) {//errors -> bindingResult로 교체
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            return "redirect:/login";
        }
        //컨트롤러에서 엔티티에 접근하면 안된다고 했던거같은데
        Member newMember = Member.builder().stId(memberDTO.getStId()).name(memberDTO.getName()).
                Department(memberDTO.getDepartment()).stGalleryNickname(memberDTO.getStGalleryNickname()).build();
        memberService.enroll(newMember);
        return "login";
    }
    @PostMapping("/join")
    public String join(Model model) {

        return "join";
    }
}
