package com.example.seoultechInvestment.service;

import com.example.seoultechInvestment.entity.CustomUserDetails;
import com.example.seoultechInvestment.entity.Member;
import com.example.seoultechInvestment.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String stId) throws UsernameNotFoundException {
        log.debug("====== loadUserByUsername 실행, 요청된 username: " + stId + " ======");

        Member findMember = memberRepository.findByStId(Long.parseLong(stId)).orElseThrow(() -> {
            log.debug("====== DB에서 " + stId + " 사용자를 찾지 못했습니다. ======");
            return new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + stId);
        });

        log.debug("====== DB에서 " + stId + " 사용자를 성공적으로 찾았습니다. ======");
        // Spring Security가 사용할 UserDetails 객체로 변환하여 반환
        return new CustomUserDetails(findMember);
    }
}
