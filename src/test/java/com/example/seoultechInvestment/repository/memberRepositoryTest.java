package com.example.seoultechInvestment.repository;

import com.example.seoultechInvestment.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class memberRepositoryTest {
    private MemberRepository memberRepository;
    @Autowired
    public memberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Test
    @DisplayName("Optional.of으로 반환해도되는지 알아보기")
    public void findByStId(){
        //given
        Member member = new Member(21101165L, "권용준", "컴퓨터공학과", "곽바닥");
        //when
        memberRepository.save(member);
        //Optional<Member> findMember = memberRepository.findByStId(21101164L);
        //when, then
        //memberRepository.findByStId(21101164L);
        Assertions.assertThatThrownBy(() -> memberRepository.findByStId(21101164L)).isInstanceOf(NullPointerException.class);
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, ()->{memberRepository.findByStId(21101164L);});
        Assertions.assertThatThrownBy(() -> memberRepository.findByStId(21101164L)).isInstanceOf(RuntimeException.class);
    }
}