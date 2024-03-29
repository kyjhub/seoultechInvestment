package com.example.seoultechInvestment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue
    private UUID id;
    @NotBlank(message = "종목명이 입력되지 않았습니다.")
    private String tickerName; //티커명은 트레이딩뷰 기준
    @NotBlank(message = "추천등록날짜가 입력되지 않았습니다.") //근데 이건 자동으로 등록될거라 굳이 검증 안해도 되는데 통신 오류로 발생할 수 있으니까
    @DateTimeFormat(pattern="yyyy-mm-dd") //등록할 때 localDate.now
    private LocalDate localDate;
    @NotBlank(message = "tp값이 입력되지 않았습니다.")
    private Long tp;    //예상 최소도달가
    private Long sellPrice; //판매가 <= 이건 후에 판매하면 업데이트
    private double rateOfReturn; //수익률 <= 이것도 후에 판매하면 업데이트
}
