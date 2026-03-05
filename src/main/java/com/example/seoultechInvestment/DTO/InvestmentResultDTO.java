package com.example.seoultechInvestment.DTO;

import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.validator.StatusPattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentResultDTO {
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate enrollDate;
    @NotNull()
    private BigDecimal sellPrice;
}
