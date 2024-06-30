package com.example.seoultechInvestment;

import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class InitDB {
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void init(){
        log.info("init method 실행");
        Member initMember = Member.builder().stId(21101165L).Department("컴퓨터공학과").password("1234")
                .stGalleryNickname("곽붕이").name("권용준").build();
        memberRepository.save(initMember);
    }
}
