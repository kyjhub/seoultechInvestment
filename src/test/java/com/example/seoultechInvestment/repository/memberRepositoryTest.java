package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
class memberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Optional.of으로 반환해도되는지 알아보기")
    public void findByStId(){
        //given
        Member member = new Member(21101165L, "권용준", "컴퓨터공학과", "곽바닥");
        //when
        //Optional<Member> findMember = memberRepository.findByStId(21101164L);
        //when, then
        try {
            memberRepository.findByStId(21101164L);
        } catch (NullPointerException nullPointerException) {
            log.error("fucking idiot, don't use Optional.of");
        }
    }
}