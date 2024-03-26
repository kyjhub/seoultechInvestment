package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@Slf4j
@Import({MemberRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RepositoryUnitTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Order(1)
    @DisplayName("save, findByStId 메서드 단위 테스트")
    public void findByStId() {
        log.info("findByStId 테스트 실행");
        //given
        Member member = new Member(21101165L, "권용준", "컴퓨터공학과", "권용준");
        //when
        memberRepository.save(member);
        //then
        Long stId = member.getStId();
        Optional<Member> findMember = memberRepository.findByStId(stId);
        Long findStId = findMember.get().getStId();

        Assertions.assertThat(stId).isEqualTo(findStId);
        log.info("findByStId 테스트 끝");
    }

    @Test
    @Order(2)
    @DisplayName("findAll 메서드 단위 테스트")
    public void findAll() {
        log.info("findAll 테스트 실행");
        //given
        Member member1 = new Member(21101165L, "권용준", "컴퓨터공학과", "권용준");
        Member member2 = new Member(21101164L, "김곽붕", "컴퓨터공학과", "김곽붕");
        Member member3 = new Member(21101163L, "안곽붕", "컴퓨터공학과", "안곽붕");
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        //then
        List<Member> findAllMember = memberRepository.findAll();
        for (Member member : findAllMember) {
            System.out.println(member.getName());
        }
    }

    @Test
    @Order(3)
    @DisplayName("delete 메서드 단위 테스트")
    public void delete() {
        log.info("delete 테스트 실행");
        //given
        Member member1 = new Member(21101165L, "권용준", "컴퓨터공학과", "권용준");
        Long stId = member1.getStId();
        memberRepository.save(member1);
        //when
        memberRepository.delete(member1);
        //then
        Assertions.assertThatThrownBy(() -> {
            memberRepository.findByStId(stId);
        }).isInstanceOf(NoResultException.class);
        //query문을 날렸을 때 데이터가 없는 경우의 예외=NoResultException
    }
}