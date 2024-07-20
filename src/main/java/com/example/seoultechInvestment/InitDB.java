package com.example.seoultechInvestment;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.InvestmentRepository;
import com.example.seoultechInvestment.repository.MemberRepository;
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
    private final InvestmentRepository investmentRepository;

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
        log.info("완성된 투자종목 초기화 실행");
        Investment prebuiltEndedInv = Investment.builder().enrollDate(LocalDate.of(2024, 3, 24))
                .stock(Stock.builder().tickerName("엘지화학").build())
                .holdTerm("180D").sellPrice(500000).earningRate(50)
                .status(ProgressStatus.SUCCESS).build();
        Investment prebuiltEndedInv2 = Investment.builder().enrollDate(LocalDate.of(2024, 7, 1))
                .stock(Stock.builder().tickerName("233740").build())
                .holdTerm("60D").sellPrice(11000).earningRate(13)
                .status(ProgressStatus.SUCCESS).build();

        log.info("진행중 투자종목 초기화 실행");
        Investment prebuiltOnGoingInv = Investment.builder().stock(Stock.builder().tickerName("iova").build())
                .status(ProgressStatus.ONGOING).tp(80)
                .entryPrice(8.49).enrollDate(LocalDate.of(2024, 6, 16))
                .build();
        Investment prebuiltOnGoingInv2 = Investment.builder().stock(Stock.builder().tickerName("pfe").build())
                .status(ProgressStatus.ONGOING).tp(36)
                .entryPrice(35).enrollDate(LocalDate.of(2023, 5, 13))
                .build();
        investmentRepository.save(prebuiltEndedInv);
        investmentRepository.save(prebuiltEndedInv2);
        investmentRepository.save(prebuiltOnGoingInv);
        investmentRepository.save(prebuiltOnGoingInv2);
    }
}
