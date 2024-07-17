package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final EntityManager entityManager;

    public void save(Stock stock) {
        entityManager.persist(stock);
    }

    public UUID delete(Stock stock) {
        entityManager.remove(stock);
        return stock.getId();
    }

    public void flush(){
        entityManager.flush();
    }
}
