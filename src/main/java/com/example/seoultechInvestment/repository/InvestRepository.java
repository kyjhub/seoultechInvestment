package com.example.seoultechInvestment.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InvestRepository {
    private final EntityManager entityManager;


}
