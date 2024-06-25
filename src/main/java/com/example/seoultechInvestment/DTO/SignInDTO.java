package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDTO {
    // NotBlank는 String 타입만 가능
    // NotNull은  모든타입 가능
    @NotNull(message = "아이디를 입력해주세요")
    private Long stId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
