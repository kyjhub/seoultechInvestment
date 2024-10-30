package com.example.seoultechInvestment.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-security.yml")
public class TelegramConfig {
    @Value("${telegramBot.token}")
    String Token;
    @Value("${telegramBot.chatId}")
    String chatId;
    String text;
}
