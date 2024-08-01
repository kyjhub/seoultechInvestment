package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Import({StockRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockRepositoryUnitTest {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Test
    @Order(1)
    @DisplayName("findOnGoingInvestments 메서드 단위 테스트")
    public void findByTicker() {
        //given
        Investment ethusdt = Investment.builder().enrollDate(LocalDate.of(2024, 3, 15)).stock(new Stock("ETHUSDT")).entryPrice(3100)
                .tp(6000).build();
        //when
        investmentRepository.save(ethusdt);
        //then
        List<Investment> onGoingInvestments = investmentRepository.findOnGoingInvestments();
        Investment onGoingStock = onGoingInvestments.get(0);
        Assertions.assertThat(ethusdt).isSameAs(onGoingStock);
    }

    @Test
    @Order(2)
    @DisplayName("findAll 메서드 단위 테스트")
    public void findALl() {
        //given
        Investment ethusdt = Investment.builder().enrollDate(LocalDate.of(2024, 5, 15)).stock(new Stock("ETHUSDT")).entryPrice(3100)
                .tp(6000).earningRate(120).build();
        Investment kodaq150 = Investment.builder().enrollDate(LocalDate.of(2024, 6, 15)).stock(new Stock("kosdaq150")).entryPrice(1350)
                .tp(2000).earningRate(100).build();
        HashSet<Investment> invts = new HashSet<>(Arrays.asList(ethusdt, kodaq150));
        //when
        investmentRepository.save(ethusdt);
        investmentRepository.save(kodaq150);
        //then
        List<Investment> endedInvestments = investmentRepository.findEndedInvestments();
        for (Investment invt : endedInvestments) {
            if (!invts.contains(invt)) {
                throw new NullPointerException();
            } else continue;
        }
    }

    @Test
    @Order(3)
    @DisplayName("findUndefinedStocks 메서드 단위 테스트")
    public void findOnGoingStocks() {
        //given
        Stock stock1 = Stock.builder().tickerName("01234").enrollDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        Stock stock2 = Stock.builder().tickerName("06088").enrollDate(LocalDate.of(2024, 3, 29)).tp(10000L)
                .build();
        Stock stock3 = Stock.builder().tickerName("03588").enrollDate(LocalDate.of(2024, 3, 29)).tp(4000L)
                .sellPrice(4000L).rateOfReturn(0D).build();
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        //when
        List<Stock> undefinedStocks = stockRepository.findOnGoingStocks();
        //then
        Long n = undefinedStocks.stream().filter(undefinedStock -> undefinedStock.getSellPrice() != null)
                .filter(undefinedStock -> Double.valueOf(undefinedStock.getEarningRate()) != null)
                .count();
        Assertions.assertThat(n).isEqualTo(0);
    }

    @Test
    @Order(4)
    @DisplayName("delete 메서드 단위 테스트")
    public void delete() {
        //given
        Stock stock = Stock.builder().tickerName("ETHUSDT").enrollDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        stockRepository.save(stock);
        //when
        UUID deleteUuid = stockRepository.delete(stock);
        //then
        Assertions.assertThatThrownBy(() -> stockRepository.findById(deleteUuid)).isInstanceOf(NoResultException.class);
    }

    @Test
    @Order(5)
    @DisplayName("비즈니스로직 단위 테스트")
    public void businessLogicUnitTest() {
        //given
        Stock stock1 = Stock.builder().tickerName("ETHUSDT").enrollDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        //when
        stock1.initSellPrice(4000L);
        stock1.initRateOfReturn(30D);
        //then
        Long findSellPrice = stock1.getSellPrice();
        Assertions.assertThat(4000L).isEqualTo(findSellPrice);
        double findRateOfReturn = stock1.getEarningRate();
        Assertions.assertThat(30D).isEqualTo(findRateOfReturn);
    }

    @Test
    @Order(6)
    @DisplayName("findRecentStocks 메서드 단위 테스트")
    public void findRecentStockTest(){
        //when
        log.info("올해 연도 : " + Integer.toString(LocalDate.now().getYear()));
        Stock stock1 = Stock.builder().tickerName("ETHUSDT").enrollDate(LocalDate.now()).tp(6000L)
                .predictedPeriod("5months")
                .build();
        Stock stock2 = Stock.builder().tickerName("BTCUSDT").enrollDate(LocalDate.of(2023, 3, 5)).tp(20000L)
                .predictedPeriod("5months")
                .build();
        stockRepository.save(stock1);
        stockRepository.save(stock2);

        //then
        log.info("나왔어야할 결과 : " + stockRepository.findAll());
        log.info("쿼리 결과 : "+ stockRepository.findRecentStocks());

        Assertions.assertThat(stockRepository.findRecentStocks().size()).isEqualTo(1);
    }
}