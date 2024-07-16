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
@Builder(toBuilder = true)
public class Stock{
    @Id
    @GeneratedValue
    private UUID id;
    private String tickerName; //티커명은 트레이딩뷰 기준

    @Override
    public Stock clone() throws CloneNotSupportedException {
        Stock cloneStock = (Stock)super.clone();
        cloneStock.builder().tickerName(this.tickerName).
                build();
        cloneStock.id = this.id;    // id를 이렇게 넣어줘도 되는건가? 위험한거 아닌가?
        return cloneStock;
    }
}
