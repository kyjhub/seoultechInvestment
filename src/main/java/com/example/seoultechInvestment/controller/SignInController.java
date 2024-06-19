package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.SignInDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignInController {
    @PostMapping("/login")
    public String login(@Valid SignInDTO signInDTO, BindingResult bindingResult) {

        return "";
    }
}
