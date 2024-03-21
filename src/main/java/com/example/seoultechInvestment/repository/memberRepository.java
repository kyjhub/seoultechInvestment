package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class memberRepository {
        private final EntityManager entityManager;
        public void save(Member member) {
                entityManager.persist(Member member);
        }
        public Member findByStId(Long stId) {
                return (Member) entityManager.createQuery("select m from Member m where m.stId=:stId")
                        .setParameter("stId", stId).setMaxResults(1).getResultList();
        }
        public List<Member> findAll() {
                return entityManager.createQuery("select m from Member m").getResultList();
        }
        public Long delete(Member member) {
                entityManager.remove(Member member);
                return member.getStId();
        }
}
