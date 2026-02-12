package com.example.seoultechInvestment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    // 기존에 갖고있던 종목을 추가매수할 때 잔고 업데이트
    public void updateOnPurchase(BigDecimal newPrice, int newQuantity) {
        // 1. 기존 총액 계산
        BigDecimal oldTotal = this.purchasePrice.multiply(new BigDecimal(this.quantity));
        // 2. 신규 매수 총액 계산
        BigDecimal newTotal = newPrice.multiply(new BigDecimal(newQuantity));

        // 3. 전체 수량 합산
        this.quantity += newQuantity;

        // 4. 새로운 평균 단가 계산 (총액 합계 / 전체 수량)
        this.purchasePrice = oldTotal.add(newTotal)
                .divide(new BigDecimal(this.quantity), 2, RoundingMode.HALF_UP);
    }
}
