package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Holding;
import com.example.seoultechInvestment.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

        private final EntityManager entityManager;

        public void save(Member member) {
                entityManager.persist(member);
        }

        public Member findByMemberId(UUID memberId){
                return (Member)entityManager.createQuery("select m from Member m where m.id=:memberId")
                        .setParameter("memberId", memberId)
                        .getSingleResult();
        }

        public Optional<Member> findByStId(Long stId) {
                return Optional.ofNullable((Member)entityManager.createQuery("select m from Member m where m.stId=:stId")
                        .setParameter("stId", stId).getSingleResult());
        }

        public List<Member> findAll() {
                return entityManager.createQuery("select m from Member m").getResultList();
        }

        public boolean existsByStId(Long stId) {
                Long stIdExisted = (Long)entityManager.createQuery("select m.stId from Member m where m.stId=:stId")
                        .setParameter("stId", stId).getResultList().stream().findFirst().orElse(-1L);
                return stIdExisted==stId;
        }

        // 다대다 연관관계 n+1문제를 해결하기 위해 한번에 가져오기(fetch join)
        // 계좌 잔고를 조회할 때 이 메서드를 호출한다. 잔고에서 현재가와 매입가를 보여주기 때문에 stock entity의 stock의 현재가 멤버변수가 필요하다.
        // 그렇기 때문에 stock도 한번에 같이 조회하는 fetch join을 사용한다.
        // 일반 join을 하면 stock entity는 비어있는 객체인 프록시 객체로 가져오기 때문이다.
        public List<Holding> findAllHoldingsByMemberId(UUID memberId) {
                return entityManager.createQuery("SELECT h FROM Holding h JOIN FETCH h.stock WHERE h.member.id = :memberId")
                        .setParameter("memberId", memberId).getResultList();
        }

        public Long delete(Member member) {
                entityManager.remove(member);
                return member.getStId();
        }
}
