package com.example.seoultechInvestment.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomeLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 1. 인증된 사용자의 권한(Role) 정보를 가져옵니다.
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // 2. 권한 목록을 순회하며 'ROLE_ADMIN'이 있는지 확인합니다.
        boolean isAdmin = authorities.stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        // 3. 권한에 따라 리다이렉트할 URL을 결정합니다.
        if (isAdmin) {
            // 관리자라면 관리자 홈 페이지로 리다이렉트
            response.sendRedirect("/admin/home");
        } else {
            // 일반 사용자라면 일반 홈 페이지로 리다이렉트
            response.sendRedirect("/home");
        }
    }
}
