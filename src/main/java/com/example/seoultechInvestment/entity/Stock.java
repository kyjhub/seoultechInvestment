package com.example.seoultechInvestment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
public class Stock {
    @Id
    @NotBlank(message = "추천등록날짜가 입력되지 않았습니다.") //근데 이건 자동으로 등록될거라 굳이 검증 안해도 되는데 통신 오류로 발생할 수 있으니까
    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDateTime localDateTime;
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    String tickerName; //티커명은 트레이딩뷰 기준
    @NotBlank(message = "tp값이 입력되지 않았습니다.")
    Long tp;
    Long sellPrice;
    double rateOfReturn;
}
