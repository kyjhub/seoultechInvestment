package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Investment;
import com.example.seoultechInvestment.Enum.ProgressStatus;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InvestmentRepository {
    private final EntityManager entityManager;

    public void save(Investment investment) {
        entityManager.persist(investment);
    }

    public Investment findById(Long InvId) {
        return (Investment) entityManager.createQuery("select inv from Investment inv where inv.id = :invId")
                .setParameter("invId", InvId).getSingleResult();
    }

    // 단건조회라서 n+1문제 고려하지 않아도됨
    public Optional<Investment> findByTickerNameAndEnrollDate(String tickerName, LocalDate enrollDate) {
        return Optional.of((Investment) entityManager.createQuery("select inv from Investment inv where inv.startDate=:enrollDate" +
                        " and inv.stock.tickerName=:tickerName")
                .setParameter("enrollDate", enrollDate)
                .setParameter("tickerName", tickerName)
                .getSingleResult());
    }

    //n+1 문제
    public List<Investment> findOnGoingInvestments(){
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }

    //n+1 문제
    public List<Investment> findFailInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status=:status")
                .setParameter("status", ProgressStatus.FAIL)
                .getResultList();
    }

    //n+1 문제
    public List<Investment> findEndedInvestments() {
        return (List<Investment>) entityManager.createQuery("select i from Investment i where i.status!=:status")
                .setParameter("status", ProgressStatus.ONGOING)
                .getResultList();
    }
}
