package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.config.Pair.UuidLongPair;
import com.example.seoultechInvestment.entity.Holding;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.entity.Stock;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class HoldingRepository {
    private final EntityManager entityManager;

    // 멤버가 갖고있는 한 종류의 종목에 해당하는 잔고를 탐색
    public Holding findByMemberAndStock(UUID memberId, Long stockId){
        return (Holding)entityManager.createQuery("select h from Holding h where h.member.id = :memberId and h.stock = :stockId")
                .setParameter("memberId", memberId)
                .setParameter("stockId", stockId).getSingleResult();
    }

    // 종목 전량 매도시 멤버의 잔고에서 삭제
    public UuidLongPair delete(Holding holding) {
        entityManager.remove(holding);
        UuidLongPair pair = new UuidLongPair(holding.getMember().getId(), holding.getStock().getId());
        return pair;
    }

    // 종목을 신규 매수할시 멤버의 잔고에 신규 보유종목 생성
    public void save(Holding holding) {
        entityManager.persist(holding);
    }
}