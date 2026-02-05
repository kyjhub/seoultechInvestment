package com.example.seoultechInvestment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;

    private BigDecimal purchasePrice; // 사용자가 매수한 가격
    private int quantity;         // 매수 수량

    // 현재 수익률 계산 로직 (DB에 저장하지 않고 실시간 계산 권장)
    public BigDecimal getEarningRate() {
        if (purchasePrice.intValue() == 0) return new BigDecimal(0);
        BigDecimal currentPrice = stock.getCurrentPrice();
        return currentPrice.subtract(purchasePrice).divide(purchasePrice).multiply(new BigDecimal(100));
    }
}
