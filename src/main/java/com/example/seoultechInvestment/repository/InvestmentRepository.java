package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InvestmentRepository {
    private final EntityManager entityManager;

    public void save(Investment investment) {
        entityManager.persist(investment);
    }

    @Transactional(readOnly = true)
    public List<Investment> findOnGoingInvestments(){
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Investment> findFailInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.FAIL)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Investment> findEndedInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status!=:status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }

    public void flush(){
        entityManager.flush();
    }
}
