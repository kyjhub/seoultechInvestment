package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
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
        return (List<Stock>) entityManager.createQuery("select s from Stock s where s.sellPrice=:sellPrice and s.rateOfReturn=:rateOfReturn")
                .setParameter("sellPrice", null).setParameter("rateOfReturn", null).getResultList();
    }
    public List<Stock> findRecentStocks() {
        return entityManager.createQuery("select s from Stock s where SUBSTRING(s.enrollDate, 1, 4) =:thisYear")  //.getYear()
                .setParameter("thisYear", Integer.toString(LocalDate.now().getYear())).getResultList();
    }

    public List<Stock> findRecentStocksTwo() {
        String thisYear = "2024";
        return entityManager.createQuery("select s from Stock s where SUBSTRING(cast(s.enrollDate as string ), 1, 4) =:thisYear")  //.getYear()
                .setParameter("thisYear", thisYear).getResultList();
    }
    public UUID delete(Stock stock) {
        entityManager.remove(stock);
        return stock.getId();
    }
}
