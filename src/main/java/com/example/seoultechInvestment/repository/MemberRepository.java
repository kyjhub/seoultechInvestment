package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
        private final EntityManager entityManager;
        public void save(Member member) {
                entityManager.persist(member);
        }
        public Optional<Member> findByStId(Long stId) {
                return Optional.of((Member)entityManager.createQuery("select m from Member m where m.stId=:stId")
                        .setParameter("stId", stId).getSingleResult());
        }
        public List<Member> findAll() {
                return entityManager.createQuery("select m from Member m").getResultList();
        }
        public Long delete(Member member) {
                entityManager.remove(member);
                return member.getStId();
        }
}
