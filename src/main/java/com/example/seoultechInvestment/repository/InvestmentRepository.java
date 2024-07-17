package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.entity.ProgressStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class InvestmentRepository {
    private final EntityManager entityManager;

    public void save(Investment investment) {
        entityManager.persist(investment);
    }

    public List<Investment> findOnGoingInvestments(){
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }

    public List<Investment> findAccomplishedInvestments(){
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.SUCCESS)
                .getResultList();
    }

    public List<Investment> findFailInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.FAIL)
                .getResultList();
    }

    public List<Investment> findEndedInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status!=status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }

    public void flush(){
        entityManager.flush();
    }
}
