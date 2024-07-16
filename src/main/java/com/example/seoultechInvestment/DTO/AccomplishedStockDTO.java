package com.example.seoultechInvestment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccomplishedStockDTO {
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName;
    @NotBlank()
    private double sellPrice;
    @NotBlank
    private double earningRate;
    @NotBlank
    private double entryPrice;
    @NotBlank
    private LocalDate enrollDate;
}
