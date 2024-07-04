package com.example.seoultechInvestment;

import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.MemberRepository;
import com.example.seoultechInvestment.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitDB {
    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initMember(){
        log.info("Member 초기화 실행");
        Member initMember = Member.builder().stId(21101165L).Department("컴퓨터공학과").password("1234")
                .stGalleryNickname("곽붕이").name("권용준").build();
        memberRepository.save(initMember);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initStock(){
        log.info("Stock 초기화 실행");
        Stock initStock = Stock.builder().tickerName("네이버").tp(100000L).enrollDate(LocalDate.now())
                .predictedPeriod("3m")
                .build();
        stockRepository.save(initStock);
    }
}
