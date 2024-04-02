package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    public void enroll(Stock stock) {
        List<Stock> undefinedStocks = stockRepository.findUndefinedStocks();
        for (Stock undefinedStock : undefinedStocks) {
            if (stock.getTickerName() == undefinedStock.getTickerName()) {
                throw new IllegalStateException("아직 이전 예측이 존재합니다.");
            }
        }
    }
    public Stock findById(UUID uuid) {
        return (Stock) stockRepository.findById(uuid).get();
    }
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
