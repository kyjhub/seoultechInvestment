package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.AccomplishedStockDTO;
import com.example.seoultechInvestment.DTO.EnrollEarningRateDTO;
import com.example.seoultechInvestment.DTO.EnrollStockDTO;
import com.example.seoultechInvestment.DTO.OnGoingStockDTO;
import com.example.seoultechInvestment.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stock")
    public String enrollStock(Model model) {
        model.addAttribute("newStock", new EnrollStockDTO());
        return "enrollStock";
    }

    @PostMapping("/stock/enroll")
    public String enrollStock(@Valid EnrollStockDTO enrollStockDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getFieldErrors()) {
                log.error(error.toString());
            }
            log.info("stock.html 로 연결");
            return "/enrollStock";
        }

        OnGoingStockDTO onGoingStockDTO = OnGoingStockDTO.builder().tickerName(enrollStockDTO.getTickerName()).
                enrollDate(LocalDate.now()).
                tp(enrollStockDTO.getTp()).
                predictedPeriod(enrollStockDTO.getPredictedPeriod()).
                build();
        stockService.enrollOnGoingStock(onGoingStockDTO);
        return "/stInvestmentHome";
    }

    @GetMapping("/stock/OngoingStocks")
    @ResponseBody
    public OnGoingStockDTO presentStock() {
        log.info("/stock/enroll is getMapped");

        OnGoingStockDTO recentStock = stockService.findOnGoingStockDTO();
        log.info("보낼 데이터 : " + recentStock);
        return recentStock;
    }

    @GetMapping("/stock/resultList")
    @ResponseBody
    public List<AccomplishedStockDTO> resultList() {
        log.info(" 추천종목 성과 기록 요청");
        return stockService.findFinishedStocks();
    }

    @GetMapping("/stock/result/enroll")
    public String getEnrollResultForm(Model model) {
        model.addAttribute("finishedStock", new AccomplishedStockDTO());
        return "enrollEarningRate";
    }
    @PostMapping("/stock/result/enroll")
    public String EnrollResult(@ModelAttribute EnrollEarningRateDTO enrollEarningRateDTO){
        stockService.modify(enrollEarningRateDTO);
        return "performanceList";
    }
}
