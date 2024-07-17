package com.example.seoultechInvestment.DTO;

import com.example.seoultechInvestment.entity.ProgressStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class EndedInvDTOfromFront {
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName;
    @NotBlank
    private String holdTerm;
    @NotNull()
    private double sellPrice;
    @NotNull
    private ProgressStatus status;
}
