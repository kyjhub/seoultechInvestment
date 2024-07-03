package com.example.seoultechInvestment.controller;

import com.example.seoultechInvestment.DTO.EnrollStockDTO;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;

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
            return "redirect:/enrollStock";
        }

        Stock stock = Stock.builder().tickerName(enrollStockDTO.getTickerName()).
                enrollDate(LocalDate.now()).
                tp(enrollStockDTO.getTp()).
                predictedPeriod(enrollStockDTO.getPredictedPeriod()).
                build();
        stockService.enroll(stock);

        return "stInvestmentHome";
    }

    @GetMapping("/stock/enroll")
    @ResponseBody
    public Stock presentStock() {
        return stockService.findRecentStock();
    }
}
