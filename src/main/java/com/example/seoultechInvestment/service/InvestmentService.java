package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.EndedInvDTOfromDB;
import com.example.seoultechInvestment.DTO.EnrollDTO;
import com.example.seoultechInvestment.DTO.InvestmentResultDTO;
import com.example.seoultechInvestment.DTO.OnGoingInvDTO;
import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.InvestmentRepository;
import com.example.seoultechInvestment.repository.StockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestmentService {
    private final InvestmentRepository investmentRepository;
    private final StockRepository stockRepository;

    /** 종목 등록**/
    @Transactional
    public void enroll(EnrollDTO enrollDTO) {
        Stock stock = stockRepository.findByName(enrollDTO.getTickerName())
                .orElseThrow(() -> new IllegalArgumentException("해당 종목이 존재하지 않습니다."));

        Investment enrollInvestment = Investment.builder().tp(enrollDTO.getTp())
                .entryPrice(enrollDTO.getEntryPrice())
                .startDate(enrollDTO.getStartDate())
                .stock(stock).status(ProgressStatus.ONGOING)
                .build();
        investmentRepository.save(enrollInvestment);
    }

    /** 진행중인 투자종목 탐색 **/
    public List<OnGoingInvDTO> findOnGoingInvestments() {
        List<Investment> onGoingInvestments = investmentRepository.findOnGoingInvestments();
        List<OnGoingInvDTO> onGoingInvDTOS = new ArrayList<>();
        for (Investment inv : onGoingInvestments) {
            OnGoingInvDTO builtInv = convertInvToOnGoingInvDTO(inv);
            onGoingInvDTOS.add(builtInv);
        }
        return onGoingInvDTOS;
    }

    /**
     * 종료된 투자종목 탐색
     **/
    public List<EndedInvDTOfromDB> findEndedInvestments() {
        log.info("디비 조회 전");
        List<Investment> endedInvestments = investmentRepository.findEndedInvestments();
        log.info("디비 조회 후");
        List<EndedInvDTOfromDB> endedInvDTOfromDBS = new ArrayList<>();
        for (Investment inv : endedInvestments) {
            EndedInvDTOfromDB builtInv = convertInvToEndedInvDTO(inv);
            endedInvDTOfromDBS.add(builtInv);
        }
        log.info(endedInvDTOfromDBS.toString());
        return endedInvDTOfromDBS;
    }

    // 진행중인 투자 종료
    @Transactional
    public void endInvestment(InvestmentResultDTO investmentResultDTO) {
        // ID 대신 종목명과 날짜로 조회
        Investment investment = investmentRepository
                .findByTickerNameAndEnrollDate(investmentResultDTO.getTickerName(),
                        investmentResultDTO.getEnrollDate())
                .orElseThrow(() -> new IllegalArgumentException("해당 날짜에 등록된 종목을 찾을 수 없습니다."));

        // dirty checking
        investment.endInvestment(investmentResultDTO.getSellPrice());
    }

    //진행중인 투자(investment entity)를 DTO로 변환
    public OnGoingInvDTO convertInvToOnGoingInvDTO(Investment investment) {
        return OnGoingInvDTO.builder()
                .tickerName(investment.getStock().getTickerName())
                .tp(investment.getTp()).enrollDate(investment.getStartDate())
                .entryPrice(investment.getEntryPrice()).build();
    }

    //종료된 투자(investment entity)를 DTO로 변환
    public EndedInvDTOfromDB convertInvToEndedInvDTO(Investment inv) {
        Period period = Period.between(inv.getStartDate(), inv.getEndDate());

        Map<String, Integer> holdTermMap = new HashMap<>();
        holdTermMap.put("years", period.getYears());
        holdTermMap.put("months", period.getMonths());
        holdTermMap.put("days", period.getDays());

        return EndedInvDTOfromDB.builder().tickerName(inv.getStock().getTickerName())
                .sellPrice(inv.getSellPrice()).holdTerm(holdTermMap)
                .enrollDate(inv.getStartDate()).earningRate(inv.getEarningRate())
                .status(inv.getStatus()).build();
    }
}
