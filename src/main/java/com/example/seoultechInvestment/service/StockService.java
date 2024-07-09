package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.StockDTO;
import com.example.seoultechInvestment.entity.Stock;
import com.example.seoultechInvestment.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {
    private final StockRepository stockRepository;
    @Transactional
    public void enroll(StockDTO stockDTO) {
        List<Stock> undefinedStocks = stockRepository.findOnGoingStocks();
        for (Stock undefinedStock : undefinedStocks) {
            if (stockDTO.getTickerName() == undefinedStock.getTickerName()) {
                throw new IllegalStateException("아직 이전 예측이 존재합니다.");
            }
        }
        Stock stock = Stock.builder().tickerName(stockDTO.getTickerName()).
                enrollDate(LocalDate.now()).
                tp(stockDTO.getTp()).
                predictedPeriod(stockDTO.getPredictedPeriod()).
                build();
        stockRepository.save(stock);
    }
    public Stock findById(UUID uuid) {
        return (Stock) stockRepository.findById(uuid).get();
    }
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public StockDTO findRecentStock() {

        List<Stock> recentStocks = stockRepository.findRecentStocks();
        log.info("올해 등록된 종목 개수 : "+recentStocks.size());
        Stock findStock = recentStocks.get(0);
        for(Stock stock : recentStocks) {
            if (!findStock.getEnrollDate().isAfter(stock.getEnrollDate())) {
                findStock = stock.toBuilder().build();  // deepCopy
            }
        }
        StockDTO findStockDTO = StockDTO.builder().tickerName(findStock.getTickerName()).
                tp(findStock.getTp()).
                enrollDate(findStock.getEnrollDate()).
                predictedPeriod(findStock.getPredictedPeriod()).build();
        return findStockDTO;
    }

    public void matchContextAndDB(){
        stockRepository.flush();
    }

}
