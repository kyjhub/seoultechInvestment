package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.*;
import com.example.seoultechInvestment.service.InvestmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class InvestController {

    private final InvestmentService investmentService;

    @GetMapping("/stock")
    public String enrollStock(Model model) {
        model.addAttribute("newStock", new EnrollDTO());
        return "enrollStock";
    }

    @PostMapping("/stock/enroll")
    public String enrollStock(@Valid EnrollDTO enrollDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            log.info("stock.html 로 연결");
            return "/enrollStock";
        }

        EnrollDTO enrollDTO1 = enrollDTO.toBuilder().enrollDate(LocalDate.now()).build();
        investmentService.enroll(enrollDTO1);

        return "/stInvestmentHome";
    }

    @GetMapping("/stock/onGoing")
    @ResponseBody
    public List<OnGoingInvDTO> presentStock() {
        log.info("/stock/enroll is getMapped");
        List<OnGoingInvDTO> onGoingInvestments = investmentService.findOnGoingInvestments();
        return onGoingInvestments;
    }

    @GetMapping("/stock/result")
    @ResponseBody
    public List<EndedInvDTOfromDB> resultList() {
        log.info("추천종목 성과 기록 요청");
        return investmentService.findEndedInvestments();
    }

//    @ModelAttribute("statuses")
//    public ProgressStatus[] statuses() {
//        return ProgressStatus.values();
//    }

    @GetMapping("/stock/result/enroll")
    public String getEnrollResultForm(Model model) {
        model.addAttribute("endedInv", new EndedInvDTOfromFront());
        return "enrollResult";
    }

    @PostMapping("/stock/result/enroll")
    public String EnrollResult(@ModelAttribute EndedInvDTOfromFront endedInvDTOfromFront) {
        investmentService.modify(endedInvDTOfromFront);
        return "performanceList";
    }
}
