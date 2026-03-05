package com.example.seoultechInvestment;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.InvestmentRepository;
import com.example.seoultechInvestment.repository.MemberRepository;
import com.example.seoultechInvestment.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitDB {
    private final MemberRepository memberRepository;
    private final InvestmentRepository investmentRepository;
    private final StockRepository stockRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initMember(){
        log.info("Member 초기화 실행");
        Member initAdmin = Member.builder().stId(21101165L).Department("컴퓨터공학과").password(passwordEncoder.encode("1234"))
                .role("ROLE_ADMIN").name("권용준").stEmail("1234@seoultech.ac.kr").build();
        Member initMember = Member.builder().stId(23423412L).Department("컴퓨터공학과").password(passwordEncoder.encode("5678"))
                .role("ROLE_USER").name("아무개").stEmail("5678@seoultech.ac.kr").build();
        memberRepository.save(initAdmin);
        memberRepository.save(initMember);

        log.info("session loginMember 생성");

    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initStockAndInvestment(){

        log.info("증권 종목 초기화");
        Stock lgChem = stockRepository.save(Stock.builder().tickerName("엘지화학").currentPrice(new BigDecimal("10000")).build());
        Stock kosdaq150 = stockRepository.save(Stock.builder().tickerName("233740").currentPrice(new BigDecimal("10000")).build());
        Stock btc = stockRepository.save(Stock.builder().tickerName("BTCUSDT").currentPrice(new BigDecimal("10000")).build());
        Stock sol = stockRepository.save(Stock.builder().tickerName("SOLUSDT").currentPrice(new BigDecimal("10000")).build());
        Stock eth = stockRepository.save(Stock.builder().tickerName("ETHUSDT").currentPrice(new BigDecimal("1950")).build());

        log.info("완성된 투자종목 초기화 실행");
        Investment prebuiltEndedInv = Investment.builder().startDate(LocalDate.of(2024, 3, 24))
                .endDate(LocalDate.of(2024, 12, 24)).stock(lgChem).sellPrice(new BigDecimal("500000")).earningRate(new BigDecimal("50"))
                .status(ProgressStatus.SUCCESS).build();
        Investment prebuiltEndedInv2 = Investment.builder().startDate(LocalDate.of(2024, 7, 1))
                .endDate(LocalDate.of(2024, 12, 1)).stock(kosdaq150).sellPrice(new BigDecimal("11000")).earningRate(new BigDecimal("13"))
                .status(ProgressStatus.SUCCESS).build();

        log.info("진행중 투자종목 초기화 실행");
        Investment prebuiltOnGoingInv = Investment.builder().stock(btc)
                .status(ProgressStatus.ONGOING).tp(new BigDecimal("140000"))
                .entryPrice(new BigDecimal("57000.00")).startDate(LocalDate.of(2024, 6, 16))
                .build();
        Investment prebuiltOnGoingInv2 = Investment.builder().stock(sol)
                .status(ProgressStatus.ONGOING).tp(new BigDecimal("300"))
                .entryPrice(new BigDecimal("150")).startDate(LocalDate.of(2024, 5, 13))
                .build();

        investmentRepository.save(prebuiltEndedInv);
        investmentRepository.save(prebuiltEndedInv2);
        investmentRepository.save(prebuiltOnGoingInv);
        investmentRepository.save(prebuiltOnGoingInv2);
    }
}
