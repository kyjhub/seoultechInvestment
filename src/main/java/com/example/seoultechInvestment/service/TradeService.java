package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.entity.AccountBalance;
import com.example.seoultechInvestment.entity.Holding;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.BalanceRepository;
import com.example.seoultechInvestment.repository.HoldingRepository;
import com.example.seoultechInvestment.repository.MemberRepository;
import com.example.seoultechInvestment.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TradeService {
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final HoldingRepository holdingRepository;
    private final BalanceRepository balanceRepository; // 추가된 레포지토리

    @Transactional
    public void buyStock(UUID memberId, Long stockId, int quantity) {
        // 1. 현금 잔고 엔티티 조회
        AccountBalance balance = balanceRepository.findById(memberId, stockId)
                .orElseThrow(() -> new IllegalArgumentException("계좌 정보가 없습니다."));

        // 2. 주식 종목 조회
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("종목 정보가 없습니다."));

        BigDecimal currentPrice = stock.getCurrentPrice();
        BigDecimal totalCost = currentPrice.multiply(BigDecimal.valueOf(quantity));

        // 3. 현금 잔고 확인 및 차감 (AccountBalance 엔티티가 처리)
        if(balance.getCashAmount().compareTo(totalCost) < 0) {
            throw new IllegalStateException("현금 잔고가 부족합니다.");
        }
        balance.withdraw(totalCost);

        // 4. Holding 저장에 필요한 Member 객체 가져오기
        // findById 대신 getReferenceById를 쓰면 DB 조회를 생략하고 프록시(가짜) 객체만 가져와서 성능을 높일 수 있습니다.
        Member member = memberRepository.findByMemberId(memberId);

        // 5. 보유 주식(Holding) 업데이트 또는 신규 생성
        Holding holding = holdingRepository.findByMemberAndStock(member.getId(), stock.getId())
                .orElse(null);

        if (holding != null) {
            // 더티체킹 + 서비스단계에서 추매한 이후의 평단가랑 수량 계산해서 넘겨줘야됨
            holding.updateOnPurchase(currentPrice, quantity);
        } else {
            Holding newHolding = Holding.builder()
                    .member(member)
                    .stock(stock)
                    .purchasePrice(currentPrice)
                    .quantity(quantity)
                    .build();
            holdingRepository.save(newHolding);
        }
    }
}
