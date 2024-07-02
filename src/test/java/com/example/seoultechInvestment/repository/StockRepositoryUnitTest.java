package com.example.seoultechInvestment.repository;

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
    private StockRepository stockRepository;

    @Test
    @Order(1)
    @DisplayName("save, findByTicker 메서드 단위 테스트")
    public void findByTicker() {
        //given
        Stock stock = Stock.builder().tickerName("ETHUSDT").localDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .sellPrice(4000L).rateOfReturn(30L).build();
        //when
        stockRepository.save(stock);
        //then
        Stock findStock = stockRepository.findById(stock.getId()).get();
        Assertions.assertThat(stock).isSameAs(findStock);
    }

    @Test
    @Order(2)
    @DisplayName("findAll 메서드 단위 테스트")
    public void findALl() {
        //given
        Stock stock1 = Stock.builder().tickerName("ETHUSDT").localDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        Stock stock2 = Stock.builder().tickerName("s&p500인버스").localDate(LocalDate.of(2024, 3, 29)).tp(10000L)
                .build();
        HashSet<Stock> stocks = new HashSet<>(Arrays.asList(stock1, stock2));
        //when
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        //then
        List<Stock> findAllStocks = stockRepository.findAll();
        for (Stock stock : findAllStocks) {
            if (!stocks.contains(stock)) {
                throw new NullPointerException();
            } else continue;
        }
    }

    @Test
    @Order(3)
    @DisplayName("findUndefinedStocks 메서드 단위 테스트")
    public void findOnGoingStocks() {
        //given
        Stock stock1 = Stock.builder().tickerName("01234").localDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        Stock stock2 = Stock.builder().tickerName("06088").localDate(LocalDate.of(2024, 3, 29)).tp(10000L)
                .build();
        Stock stock3 = Stock.builder().tickerName("03588").localDate(LocalDate.of(2024, 3, 29)).tp(4000L)
                .sellPrice(4000L).rateOfReturn(0D).build();
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        //when
        List<Stock> undefinedStocks = stockRepository.findOnGoingStocks();
        //then
        Long n = undefinedStocks.stream().filter(undefinedStock -> undefinedStock.getSellPrice() != null)
                .filter(undefinedStock -> Double.valueOf(undefinedStock.getRateOfReturn()) != null)
                .count();
        Assertions.assertThat(n).isEqualTo(0);
    }

    @Test
    @Order(4)
    @DisplayName("delete 메서드 단위 테스트")
    public void delete() {
        //given
        Stock stock = Stock.builder().tickerName("ETHUSDT").localDate(LocalDate.of(2024, 3, 15)).tp(4600L)
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
        Stock stock1 = Stock.builder().tickerName("ETHUSDT").localDate(LocalDate.of(2024, 3, 15)).tp(4600L)
                .build();
        //when
        stock1.initSellPrice(4000L);
        stock1.initRateOfReturn(30D);
        //then
        Long findSellPrice = stock1.getSellPrice();
        Assertions.assertThat(4000L).isEqualTo(findSellPrice);
        double findRateOfReturn = stock1.getRateOfReturn();
        Assertions.assertThat(30D).isEqualTo(findRateOfReturn);
    }
}