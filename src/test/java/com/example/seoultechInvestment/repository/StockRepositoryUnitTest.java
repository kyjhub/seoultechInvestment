package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Stock;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.Optional;

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
        Stock stock = new Stock("ETHUSDT", LocalDate.of(2024, 3, 15), 4600L, 4000L, 30L);
        //when
        stockRepository.save(stock);
        //then
        Stock findStock = stockRepository.findByTicker(stock).get();
        findStock.get
        Assertions.assertThat(stock).isEqualTo(findStock);
    }

}