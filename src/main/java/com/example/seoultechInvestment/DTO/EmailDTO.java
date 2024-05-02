package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDTO {
    @Email
    @NotBlank(message = "이메일 입력은 필수입니다.")
    public String email;
}
