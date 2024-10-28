package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.EndedInvDTOfromDB;
import com.example.seoultechInvestment.DTO.EndedInvDTOfromFront;
import com.example.seoultechInvestment.DTO.EnrollDTO;
import com.example.seoultechInvestment.DTO.OnGoingInvDTO;
import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.InvestmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvestmentService {
    private final InvestmentRepository investmentRepository;

    /** 종목 등록**/
    @Transactional
    public void enroll(EnrollDTO enrollDTO) {
        Stock stock = new Stock(enrollDTO.getTickerName());
        Investment enrollInvestment = Investment.builder().tp(enrollDTO.getTp())
                .entryPrice(enrollDTO.getEntryPrice())
                .enrollDate(enrollDTO.getEnrollDate())
                .stock(stock).status(ProgressStatus.ONGOING)
                .build();
        investmentRepository.save(enrollInvestment);
    }

    public List<OnGoingInvDTO> findOnGoingInvestments() {
        List<Investment> onGoingInvestments = investmentRepository.findOnGoingInvestments();
        List<OnGoingInvDTO> onGoingInvDTOS = new ArrayList<>();
        for (Investment inv : onGoingInvestments) {
            OnGoingInvDTO builtInv = convertInvToOnGoingInvDTO(inv);
            onGoingInvDTOS.add(builtInv);
        }
        return onGoingInvDTOS;
    }

    public List<EndedInvDTOfromDB> findEndedInvestments() {
        List<Investment> endedInvestments = investmentRepository.findEndedInvestments();
        List<EndedInvDTOfromDB> endedInvDTOfromDBS = new ArrayList<>();
        for (Investment inv : endedInvestments) {
            EndedInvDTOfromDB builtInv = convertInvToEndedInvDTO(inv);
            endedInvDTOfromDBS.add(builtInv);
        }
        return endedInvDTOfromDBS;
    }

    @Transactional
    public void modify(EndedInvDTOfromFront endedInvDTOfromFront) {
        List<Investment> onGoingInvs = investmentRepository.findOnGoingInvestments();
        String findName = endedInvDTOfromFront.getTickerName();
        Investment invToModify = onGoingInvs.stream().filter(inv -> findName.equals(inv.getStock()
                        .getTickerName())).findAny()
                        .orElseThrow();
        double earningRate = endedInvDTOfromFront.getSellPrice() - invToModify.getEntryPrice();
        invToModify.addInfoAfterEnd(
                endedInvDTOfromFront.getSellPrice(),
                endedInvDTOfromFront.getHoldTerm(),
                earningRate,
                earningRate>0 ? ProgressStatus.SUCCESS : ProgressStatus.FAIL
        );

        // 이걸 꼭 넣어야되는걸까?
        investmentRepository.flush();
    }

    public OnGoingInvDTO convertInvToOnGoingInvDTO(Investment investment) {
        return OnGoingInvDTO.builder()
                .tickerName(investment.getStock().getTickerName())
                .tp(investment.getTp()).enrollDate(investment.getEnrollDate())
                .entryPrice(investment.getEntryPrice()).build();
    }

    public EndedInvDTOfromDB convertInvToEndedInvDTO(Investment inv) {
        return EndedInvDTOfromDB.builder().tickerName(inv.getStock().getTickerName())
                .sellPrice(inv.getSellPrice()).holdTerm(inv.getHoldTerm())
                .enrollDate(inv.getEnrollDate()).earningRate(inv.getEarningRate())
                .status(inv.getStatus()).build();
    }
}
