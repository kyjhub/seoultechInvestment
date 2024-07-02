package com.example.seoultechInvestment.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PerformanceStockDTO {
    private String tickerName;
    private LocalDate enrollDate;
    private Long tp;
    private Long sellPrice;
    private double rateOfReturn;
}
