package com.example.seoultechInvestment.entity;

import com.example.seoultechInvestment.Enum.ProgressStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProgressStatus status;

    /** 투자시작할 때 입력할 정보 **/
    private BigDecimal entryPrice;  // 추천 진입가 만약에 이 가격을 오지 않는다면
    private BigDecimal tp;    //최소 도달가
    private LocalDate startDate;
    
    /** 투자 끝날 때 입력할 정보 **/
    private BigDecimal sellPrice; // 판매가 <= 이건 매도하면 업데이트
    private LocalDate endDate;
    private BigDecimal earningRate; // 수익률 <= 이것도 후에 판매하면 업데이트

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="STOCK_ID")    // 다대일 단방향
    private Stock stock;

    @Override
    public Investment clone() throws CloneNotSupportedException {
        Investment cloneInv = (Investment) super.clone();
        cloneInv.builder().startDate(this.startDate).
                endDate(this.endDate).
                tp(this.tp).earningRate(this.earningRate).
                sellPrice(this.sellPrice).
                status(this.status).
                earningRate(this.earningRate).
                build();
        cloneInv.id = this.id;    // id를 이렇게 넣어줘도 되는건가? 위험한거 아닌가?
        return cloneInv;
    }

    //매도시 판매가 삽입
    public void endInvestment(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
        this.endDate = LocalDate.now();
        // 수익률은 소수점 2째자리까지 반올림해서 표시
        this.earningRate = this.sellPrice.subtract(this.entryPrice).divide(this.entryPrice, 2, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
        if (this.sellPrice.compareTo(this.entryPrice) > 0) {
            this.status = ProgressStatus.SUCCESS;
        }else {
            this.status = ProgressStatus.FAIL;
        }
    }
}
