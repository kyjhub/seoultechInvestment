package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.Enum.ProgressStatus;
import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.entity.Stock;
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
    public void findOnGoingInvestments() {
        //given
        Investment ethusdt = Investment.builder().enrollDate(LocalDate.of(2024, 3, 15)).stock(new Stock("ETHUSDT")).entryPrice(3100)
                .tp(6000).status(ProgressStatus.ONGOING).build();
        //when
        investmentRepository.save(ethusdt);
        //then
        List<Investment> onGoingInvestments = investmentRepository.findOnGoingInvestments();
        Investment onGoingStock = onGoingInvestments.get(0);
        Assertions.assertThat(ethusdt).isSameAs(onGoingStock);
    }

    @Test
    @Order(2)
    @DisplayName("findEndedInvestments 메서드 단위 테스트")
    public void findEndedInvestments() {
        //given
        Investment ethusdt = Investment.builder().enrollDate(LocalDate.of(2024, 5, 15)).stock(new Stock("ETHUSDT")).entryPrice(3100)
                .tp(6000).earningRate(120).holdTerm("100D").sellPrice(6400).status(ProgressStatus.SUCCESS).build();
        Investment kodaq150 = Investment.builder().enrollDate(LocalDate.of(2024, 6, 15)).stock(new Stock("kosdaq150")).entryPrice(1350)
                .tp(2000).earningRate(100).holdTerm("100D").sellPrice(1900).status(ProgressStatus.SUCCESS).build();
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
    @DisplayName("findFailInvestments 메서드 단위 테스트")
    public void findFailInvestments() {
        //given
        Investment ethusdt = Investment.builder().enrollDate(LocalDate.of(2024, 5, 15)).stock(new Stock("ETHUSDT")).entryPrice(3100)
                .tp(6000).earningRate(-30).holdTerm("100D").sellPrice(2500).status(ProgressStatus.FAIL).build();
        Investment kosdaq150 = Investment.builder().enrollDate(LocalDate.of(2024, 5, 15)).stock(new Stock("kosdaq150")).entryPrice(1350)
                .tp(2000).earningRate(-40).holdTerm("100D").sellPrice(1000).status(ProgressStatus.FAIL).build();
        investmentRepository.save(ethusdt);
        investmentRepository.save(kosdaq150);
        //when
        List<Investment> invts = investmentRepository.findFailInvestments();
        //then
        Long n = invts.stream().filter(failedInvt -> failedInvt.getStatus() == ProgressStatus.FAIL)
                .count();
        Assertions.assertThat(n).isEqualTo(2);
    }
}