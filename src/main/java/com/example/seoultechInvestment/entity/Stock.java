package com.example.seoultechInvestment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String tickerName; //티커명은 트레이딩뷰 기준
    @Column(nullable = false)
    private BigDecimal currentPrice;

    public Stock(String tickerName){
        this.tickerName = tickerName;
    }
}
