package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

        private final EntityManager entityManager;

        public void save(Member member) {
                entityManager.persist(member);
        }

        @Transactional(readOnly = true)
        public Optional<Member> findByStId(Long stId) {
                return Optional.ofNullable((Member)entityManager.createQuery("select m from Member m where m.stId=:stId")
                        .setParameter("stId", stId).getSingleResult());
        }

        @Transactional(readOnly = true)
        public List<Member> findAll() {
                return entityManager.createQuery("select m from Member m").getResultList();
        }

        @Transactional(readOnly = true)
        public boolean existsByStId(Long stId) {
                Long stIdExisted = (Long)entityManager.createQuery("select m.stId from Member m where m.stId=:stId")
                        .setParameter("stId", stId).getResultList().stream().findFirst().orElse(-1L);
                return stIdExisted==stId;
        }

        public Long delete(Member member) {
                entityManager.remove(member);
                return member.getStId();
        }
}
