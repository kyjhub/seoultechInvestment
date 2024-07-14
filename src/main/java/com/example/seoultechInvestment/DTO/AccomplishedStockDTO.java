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
    @NotNull(message = "추천등록날짜가 입력되지 않았습니다.") //근데 이건 자동으로 등록될거라 굳이 검증 안해도 되는데 통신 오류로 발생할 수 있으니까
    @DateTimeFormat(pattern="yyyy-mm-dd") //등록할 때 localDate.now
    private LocalDate enrollDate;
    @NotNull(message = "tp값이 입력되지 않았습니다.")
    private Long tp;
    @NotBlank
    private String predictedPeriod;
    @NotBlank
    private Double earningRate;
}
