package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UUID delete(Stock stock) {
        entityManager.remove(stock);
        return stock.getId();
    }
}
