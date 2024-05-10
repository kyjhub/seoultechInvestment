package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AthNumDTO {
    @NotBlank(message = "인증번호가 필요합니다.")
    private String athNum;
}
