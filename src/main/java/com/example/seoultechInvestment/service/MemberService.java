package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void enroll(Member member) {
        Long newStId = member.getStId();
        List<Member> allMember = memberRepository.findAll();
        for (Member m : allMember) {    //중복회원체크
            if (m.getStId() == newStId) {
                throw new IllegalStateException("이미 존재하는 회원입니다."); 
            }
        }
        memberRepository.save(member);
    }
    public Member findByStId(Long stId) {
        Optional<Member> findMemberbyStId = memberRepository.findByStId(stId);
        Member findMember = findMemberbyStId.get();
        return findMember;
    }
    public List<Member> findAll() {
        List<Member> findAllMember = memberRepository.findAll();
        return findAllMember;
    }
}
