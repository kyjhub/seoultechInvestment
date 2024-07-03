package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    @Transactional
    public void enroll(Stock stock) {
        List<Stock> undefinedStocks = stockRepository.findOnGoingStocks();
        for (Stock undefinedStock : undefinedStocks) {
            if (stock.getTickerName() == undefinedStock.getTickerName()) {
                throw new IllegalStateException("아직 이전 예측이 존재합니다.");
            }
        }
        stockRepository.save(stock);
    }
    public Stock findById(UUID uuid) {
        return (Stock) stockRepository.findById(uuid).get();
    }
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public Stock findRecentStock() {
        List<Stock> recentStocks = stockRepository.findRecentStocks();
        Stock findStock = recentStocks.get(0);
        for(Stock stock : recentStocks) {
            if (findStock.getEnrollDate().isAfter(stock.getEnrollDate())) {
                findStock = stock.toBuilder().build();  // deepCopy
            }
        }
        return findStock;
    }

}
