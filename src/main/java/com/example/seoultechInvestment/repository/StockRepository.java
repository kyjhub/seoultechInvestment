package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.DTO.OnGoingStockDTO;
import com.example.seoultechInvestment.entity.Stock;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final EntityManager entityManager;
    public void save(Stock stock) {
        entityManager.persist(stock);
    }
    public Optional<Stock> findById(UUID uuid) {
        return Optional.ofNullable((Stock) entityManager.createQuery("select s from Stock s where s.id=:primaryKey").
                setParameter("primaryKey", uuid).getSingleResult());
    }
    public List<Stock> findAll() {
        return (List<Stock>) entityManager.createQuery("select s from Stock s").getResultList();
    }

    public List<Stock> findOnGoingStocks() {
        return (List<Stock>) entityManager.createQuery("select s from Stock s where s.sellPrice=:sellPrice and s.earningRate=:rateOfReturn")
                .setParameter("sellPrice", null).setParameter("rateOfReturn", null).getResultList();
    }

    /**
     * 당일부터 등록한지 1년전까지 기한 반환하는 함수도 작성
     * */
    public List<Stock> findRecentStocks() {
        int thisYear = 2024;
        return entityManager.createQuery("select s from Stock s where function('YEAR', s.enrollDate) =:thisYear")  //.getYear()
                .setParameter("thisYear", thisYear).getResultList();
    }

    public List<Stock> findOngoingStocks(){
        return entityManager.createQuery("select s from Stock s where s.earningRate=:num")
                .setParameter("num", 0.0)
                .getResultList();
    }

    public UUID delete(Stock stock) {
        entityManager.remove(stock);
        return stock.getId();
    }

    public void flush(){
        entityManager.flush();
    }

    public Stock findStockByNameAndDate(String name, LocalDate date){
        return (Stock) entityManager.createQuery("select s from Stock s where s.tickerName=:name" +
                "and s.enrollDate=:date").
                setParameter("name", name).
                setParameter("date", date).
                getSingleResult();
    }
    public List<Stock> findCompletedStocks() {
        return entityManager.createQuery("select s from Stock s where s.earningRate!=:zero")
                .setParameter("zero", 0.0)
                .getResultList();
    }
}
