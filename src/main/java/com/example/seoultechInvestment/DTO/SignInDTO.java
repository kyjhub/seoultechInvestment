package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    private Long stId;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
