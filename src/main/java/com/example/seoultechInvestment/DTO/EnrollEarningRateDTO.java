package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollEarningRateDTO {
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName;
    @NotNull()
    private double sellPrice;
    @NotNull
    private double earningRate;
    @NotNull
    private double entryPrice;
}
