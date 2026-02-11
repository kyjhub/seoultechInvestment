package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.AccountBalance;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BalanceRepository {
    private final EntityManager entityManager;

    public AccountBalance findById(UUID memberId, Long stockId) {
        return (AccountBalance) entityManager.createQuery("select accBal from AccountBalance accBal where accBal.member.id=:memberId")
                .setParameter("memberId", memberId)
                .getSingleResult();
    }
}
