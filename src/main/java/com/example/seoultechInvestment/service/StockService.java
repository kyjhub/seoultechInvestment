package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.AccomplishedStockDTO;
import com.example.seoultechInvestment.DTO.OnGoingStockDTO;
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
    public void enroll(OnGoingStockDTO onGoingStockDTO) {
        List<Stock> undefinedStocks = stockRepository.findOnGoingStocks();
        for (Stock undefinedStock : undefinedStocks) {
            if (onGoingStockDTO.getTickerName() == undefinedStock.getTickerName()) {
                throw new IllegalStateException("아직 이전 예측이 존재합니다.");
            }
        }
        Stock stock = Stock.builder().tickerName(onGoingStockDTO.getTickerName()).
                enrollDate(LocalDate.now()).
                tp(onGoingStockDTO.getTp()).
                predictedPeriod(onGoingStockDTO.getPredictedPeriod()).
                build();
        stockRepository.save(stock);
    }
    public Stock findById(UUID uuid) {
        return (Stock) stockRepository.findById(uuid).get();
    }
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public OnGoingStockDTO findRecentStock() {

        List<Stock> recentStocks = stockRepository.findRecentStocks();
        log.info("올해 등록된 종목 개수 : "+recentStocks.size());
        Stock findStock = recentStocks.get(0);
        for(Stock stock : recentStocks) {
            if (!findStock.getEnrollDate().isAfter(stock.getEnrollDate())) {
                findStock = stock.toBuilder().build();  // deepCopy
            }
        }
        OnGoingStockDTO findOnGoingStockDTO = OnGoingStockDTO.builder().tickerName(findStock.getTickerName()).
                tp(findStock.getTp()).
                enrollDate(findStock.getEnrollDate()).
                predictedPeriod(findStock.getPredictedPeriod()).build();
        return findOnGoingStockDTO;
    }

    public void matchContextAndDB(){
        stockRepository.flush();
    }

    public List<AccomplishedStockDTO> findResult() {
        List<Stock> completedStocks = stockRepository.findCompletedStocks();
        return convertStockListToStockDTOList(completedStocks);
    }

    public AccomplishedStockDTO convertStockToStockDTO(Stock stock) {
        return AccomplishedStockDTO.builder().tickerName(stock.getTickerName()).
                tp(stock.getTp()).
                enrollDate(stock.getEnrollDate()).
                predictedPeriod(stock.getPredictedPeriod()).
                earningRate(stock.getEarningRate()).build();

    }

    public List<AccomplishedStockDTO> convertStockListToStockDTOList(List<Stock> stockList) {
        List<AccomplishedStockDTO> copyComStockDTOs = List.of();
        for (Stock stock : stockList) {
            AccomplishedStockDTO accomplishedStockDTO = convertStockToStockDTO(stock);
            copyComStockDTOs.add(accomplishedStockDTO);
        }
        return copyComStockDTOs;
    }
}
