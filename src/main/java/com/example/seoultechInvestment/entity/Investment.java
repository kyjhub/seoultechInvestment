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
public class Investment {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    /** 투자시작할 때 입력할 정보 **/
    private double entryPrice;  // 추천 진입가 만약에 이 가격을 오지 않는다면
    private double tp;    //최소 도달가
    private LocalDate enrollDate;
    
    /** 투자 끝날 때 입력할 정보 **/
    private double sellPrice; // 판매가 <= 이건 후에 판매하면 업데이트
    private String holdTerm; // "숫자D" 형식으로 제한, 이건 후에 판매하면 업데이트
    private double earningRate; // 수익률 <= 이것도 후에 판매하면 업데이트

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="STOCK_ID")    // 다대일 단방향
    private Stock stock;

    @Override
    public Investment clone() throws CloneNotSupportedException {
        Investment cloneStock = (Investment) super.clone();
        cloneStock.builder().enrollDate(this.enrollDate).
                holdTerm(this.holdTerm).
                tp(this.tp).earningRate(this.earningRate).
                sellPrice(this.sellPrice).
                status(this.status).holdTerm(this.holdTerm).
                earningRate(this.earningRate).
                build();
        cloneStock.id = this.id;    // id를 이렇게 넣어줘도 되는건가? 위험한거 아닌가?
        return cloneStock;
    }

    public void addInfoAfterEnd(double sellPrice, String holdTerm, double earningRate, ProgressStatus status) {
        this.sellPrice = sellPrice;
        this.earningRate = earningRate;
        this.holdTerm = holdTerm;
        this.status = status;
    }
}
