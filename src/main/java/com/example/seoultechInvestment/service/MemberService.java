package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.DTO.SignUpDTO;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public void enroll(SignUpDTO signUpDTO) {
        //중복 회원가입 확인
        if (memberRepository.existsByStId(signUpDTO.getSn())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        //비밀번호 암호화+디비에 저장할 member 생성
        Member signUpMember = Member.builder().stId(signUpDTO.getSn()).name(signUpDTO.getName()).
                Department(signUpDTO.getDepartment()).role("ROLE_USER").
                password(passwordEncoder.encode(signUpDTO.getPassword())).
                stEmail(signUpDTO.getStEmail()).build();
        memberRepository.save(signUpMember);
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
