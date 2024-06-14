package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    private String stId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
