package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final EntityManager entityManager;

    public Optional<Stock> findById(Long id) {
        return Optional.of((Stock)entityManager.createQuery("select s from Stock s where s.id=:id")
                .setParameter("id", id)
                .getSingleResult());
    }

    public void save(Stock stock) {
        entityManager.persist(stock);
    }

    public Long delete(Stock stock) {
        entityManager.remove(stock);
        return stock.getId();
    }

    public void flush(){
        entityManager.flush();
    }
}
