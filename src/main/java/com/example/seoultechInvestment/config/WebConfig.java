package com.example.seoultechInvestment.config;

import com.example.seoultechInvestment.Interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/home", "/signIn", "/signUp",
                        "/authEmail", "/login", "/css/**", "/*.ico",
                        "/error", "/join", "/email");
    }
}
