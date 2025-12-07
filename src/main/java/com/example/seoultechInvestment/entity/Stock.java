package com.example.seoultechInvestment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock{
    @Id
    @GeneratedValue
    private UUID id;
    private String tickerName; //티커명은 트레이딩뷰 기준

    public Stock(String tickerName){
        this.tickerName = tickerName;
    }
}
