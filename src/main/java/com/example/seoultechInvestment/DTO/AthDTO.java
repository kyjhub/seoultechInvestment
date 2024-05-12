package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class AthDTO {
    @Email
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;
    private String athNum;

    public AthDTO(String email) {
        this.email = email;
    }
}
