package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.SignInDTO;
import com.example.seoultechInvestment.Enum.Role;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(SignInDTO signInDTO) {
        Long loginStId = signInDTO.getStId();
        return memberRepository.findByStId(loginStId).orElse(null);
    }
}
