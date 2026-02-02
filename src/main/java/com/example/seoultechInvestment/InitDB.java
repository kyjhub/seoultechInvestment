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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitDB {
    private final MemberRepository memberRepository;
    private final InvestmentRepository investmentRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initAdmin(){
        log.info("Member 초기화 실행");
        Member initAdmin = Member.builder().stId(21101165L).Department("컴퓨터공학과").password(passwordEncoder.encode("1234"))
                .role("ROLE_ADMIN").name("권용준").build();
        Member initMember = Member.builder().stId(23423412L).Department("컴퓨터공학과").password(passwordEncoder.encode("5678"))
                .role("ROLE_USER").name("아무개").build();
        memberRepository.save(initAdmin);
        memberRepository.save(initMember);

        log.info("session loginMember 생성");

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
        Investment prebuiltOnGoingInv = Investment.builder().stock(Stock.builder().tickerName("BTCUSDT").build())
                .status(ProgressStatus.ONGOING).tp(140000)
                .entryPrice(57000).enrollDate(LocalDate.of(2024, 6, 16))
                .build();
        Investment prebuiltOnGoingInv2 = Investment.builder().stock(Stock.builder().tickerName("SOLUSDT").build())
                .status(ProgressStatus.ONGOING).tp(300)
                .entryPrice(150).enrollDate(LocalDate.of(2024, 5, 13))
                .build();
        investmentRepository.save(prebuiltEndedInv);
        investmentRepository.save(prebuiltEndedInv2);
        investmentRepository.save(prebuiltOnGoingInv);
        investmentRepository.save(prebuiltOnGoingInv2);
    }
}
