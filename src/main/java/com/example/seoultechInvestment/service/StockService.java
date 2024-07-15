package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.AccomplishedStockDTO;
import com.example.seoultechInvestment.DTO.EnrollEarningRateDTO;
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
    public void enrollOnGoingStock(OnGoingStockDTO onGoingStockDTO) {
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

    public List<OnGoingStockDTO> findOngoingStocks() {

        List<Stock> findOngoingStocks = stockRepository.findOngoingStocks();

        List<OnGoingStockDTO> onGoingStockDTOs = convertOnGoingStocksToDTOList(findOngoingStocks);
        return onGoingStockDTOs;
    }

    public OnGoingStockDTO findOnGoingStockDTO() {

        List<Stock> recentStocks = stockRepository.findRecentStocks();

        Stock findStock = recentStocks.get(0);
        for(Stock stock : recentStocks) {
            if (!findStock.getEnrollDate().isAfter(stock.getEnrollDate())) {
                findStock = stock.toBuilder().build();  // deepCopy
            }
        }
        return convertOnGoingStockToDTO(findStock);
    }

    public void matchContextAndDB(){
        stockRepository.flush();
    }

    public List<AccomplishedStockDTO> findFinishedStocks() {
        List<Stock> completedStocks = stockRepository.findCompletedStocks();
        return convertStocksToDTOList(completedStocks);
    }

    /**
     * 나중에 파라미터 추가
     * 반환형 리스트로 바꿔야함
     * */
    public EnrollEarningRateDTO findByNameInOngoingStocks(){
        OnGoingStockDTO recentStock = findOnGoingStockDTO();
        return EnrollEarningRateDTO.builder().tickerName(recentStock.getTickerName())
                .build();
    }

    @Transactional
    public void modify(EnrollEarningRateDTO enrollEarningRateDTO){
        String name = enrollEarningRateDTO.getTickerName();
        Stock findOnGoingStockByName = null;
        try {
            findOnGoingStockByName = findOnGoingStockByName(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {}
        findOnGoingStockByName.toBuilder().
                earningRate(enrollEarningRateDTO.getEarningRate());
        matchContextAndDB();    // 이건 해야할지 고려해봐야함
    }

    public Stock findOnGoingStockByName(String name){
        List<Stock> onGoingStocks = stockRepository.findOngoingStocks();
        for (Stock onGoingStock : onGoingStocks){
            if (name==onGoingStock.getTickerName()){
                return onGoingStock;
            }
        }
        throw new NullPointerException("현재 진행중인 종목 중에 종목명에 맞는 것이 없습니다.");
    }
    private static OnGoingStockDTO convertOnGoingStockToDTO(Stock findStock) {
        OnGoingStockDTO findOnGoingStockDTO = OnGoingStockDTO.builder().tickerName(findStock.getTickerName()).
                tp(findStock.getTp()).
                enrollDate(findStock.getEnrollDate()).
                predictedPeriod(findStock.getPredictedPeriod()).build();
        return findOnGoingStockDTO;
    }
    public List<OnGoingStockDTO> convertOnGoingStocksToDTOList(List<Stock> findOngoingStocks) {
        List<OnGoingStockDTO> onGoingStockDTOs = List.of();
        for(Stock stock : findOngoingStocks){
            OnGoingStockDTO copyOnGoingStock = OnGoingStockDTO.builder().tickerName(stock.getTickerName())
                    .tp(stock.getTp())
                    .predictedPeriod(stock.getPredictedPeriod())
                    .enrollDate(stock.getEnrollDate())
                    .build();
            onGoingStockDTOs.add(copyOnGoingStock);
        }
        return onGoingStockDTOs;
    }
    public AccomplishedStockDTO convertStockToDTO(Stock stock) {
        return AccomplishedStockDTO.builder().tickerName(stock.getTickerName()).
                tp(stock.getTp()).
                enrollDate(stock.getEnrollDate()).
                predictedPeriod(stock.getPredictedPeriod()).
                earningRate(stock.getEarningRate()).build();

    }

    public List<AccomplishedStockDTO> convertStocksToDTOList(List<Stock> stockList) {
        List<AccomplishedStockDTO> copyComStockDTOs = List.of();
        for (Stock stock : stockList) {
            AccomplishedStockDTO accomplishedStockDTO = convertStockToDTO(stock);
            copyComStockDTOs.add(accomplishedStockDTO);
        }
        return copyComStockDTOs;
    }
}
