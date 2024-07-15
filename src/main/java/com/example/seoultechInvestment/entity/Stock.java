package com.example.seoultechInvestment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Builder(toBuilder = true)
public class Stock{
    @Id
    @GeneratedValue
    private UUID id;
    private String tickerName; //티커명은 트레이딩뷰 기준
    private LocalDate enrollDate;
    private Long tp;    //예상 최소도달가
    private String predictedPeriod;
    private Double sellPrice; //판매가 <= 이건 후에 판매하면 업데이트
    private double earningRate; //수익률 <= 이것도 후에 판매하면 업데이트

    @Override
    public Stock clone() throws CloneNotSupportedException {
        Stock cloneStock = (Stock)super.clone();
        cloneStock.builder().enrollDate(this.enrollDate).
                predictedPeriod(this.predictedPeriod).
                tp(this.tp).earningRate(this.earningRate).
                sellPrice(this.sellPrice).tickerName(this.tickerName).build();
        cloneStock.id = this.id;    // id를 이렇게 넣어줘도 되는건가? 위험한거 아닌가?
        return cloneStock;
    }

    //==비즈니스 로직==//
    public void initSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }
    public void initEarningRate(Double earningRate) {
        this.earningRate = earningRate;
    }
}
