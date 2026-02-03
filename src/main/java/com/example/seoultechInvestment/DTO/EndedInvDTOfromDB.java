package com.example.seoultechInvestment.DTO;

import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.validator.StatusPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class EndedInvDTOfromDB {
    @NotNull
    private LocalDate enrollDate;
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName;
    @NotBlank
    @Pattern(regexp = "[0-9]*D$")
    private String holdTerm;
    @NotNull()
    private BigDecimal sellPrice;
    @NotNull
    private BigDecimal earningRate;
    @NotNull
    @StatusPattern
    private ProgressStatus status;
}
