package com.example.seoultechInvestment.config;

import com.example.seoultechInvestment.security.CustomeLoginSuccessHandler;
import com.example.seoultechInvestment.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomeLoginSuccessHandler customeLoginSuccessHandler;
    /**
     * 이 메소드는 정적 리소스(js, css, image 등)에 대한 보안을 완전히 무시하도록 설정합니다.
     * 이것이 바로 302 리다이렉트 문제를 해결하는 핵심 부분입니다.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // PathRequest.toStaticResources().atCommonLocations()는
        // /css/**, /js/**, /images/**, /favicon.ico 등 일반적인 정적 리소스 경로를 자동으로 처리해줍니다.
        // 여기에 사용자 정의 경로인 /html/** 를 명시적으로 추가할 수 있습니다.
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/html/**"); // 사용자 정의 폴더가 있다면 여기에 추가
    }

    /**
     * HTTP 요청에 대한 보안 규칙을 설정합니다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화 (개발 중이거나 API 서버로 사용할 때)
        http.csrf(CsrfConfigurer::disable);


        // HTTP 요청에 대한 인가 규칙 설정
        http.authorizeHttpRequests(authorize -> authorize
                        // 아래에 명시된 URL 경로는 로그인 없이 누구나 접근 가능
                        .requestMatchers("/", "/index", "/login", "/signUp", "/authEmail", "/join").permitAll()

                        // '/admin/**' 경로는 'ADMIN' 역할을 가진 사용자만 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // 위에서 설정한 경로 외의 모든 요청은 인증(로그인)된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )

                // 폼 기반 로그인 설정
                .formLogin(formLogin -> formLogin
                        // 사용자 정의 로그인 페이지 URL
                        .loginPage("/login")
                        //CustomUserDetailService의 loadUserByUserName메서드에 username으로
                        //전달해야하는 문제해결
                        .usernameParameter("stId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        // 로그인 성공 시 행동명령
                        .successHandler(customeLoginSuccessHandler)
                        //로그인 실패 후 이동할 URL
                        .failureUrl("/login?error=true")
                        .permitAll() // 로그인 페이지는 누구나 접근 가능해야 함
                )

                // 로그아웃 설정
                .logout(logout -> logout
                        // 로그아웃 성공 후 이동할 URL
                        .logoutSuccessUrl("/")
                        // 세션 무효화
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    /**
     * 비밀번호 암호화를 위한 PasswordEncoder 빈을 등록합니다.
     * Spring Security는 반드시 PasswordEncoder를 필요로 합니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}