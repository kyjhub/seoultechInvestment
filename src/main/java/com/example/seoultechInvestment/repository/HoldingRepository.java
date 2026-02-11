package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Holding;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HoldingRepository {
    private final EntityManager entityManager;

    // 멤버가 갖고있는 종목1개의 잔고 찾기
    public Holding findByMemberAndStock(Member member, Stock stock){
        return (Holding)entityManager.createQuery("select h from Holding h where h.member.id = :member and h.stock = :stock")
                .setParameter("member", member)
                .setParameter("stock", stock).getSingleResult();
    }

    // 멤버의 주식 1개 종목 잔고 저장
    public void save(Holding holding) {
        entityManager.persist(holding);
    }
}
