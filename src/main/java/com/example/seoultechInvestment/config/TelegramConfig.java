package com.example.seoultechInvestment.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource(value = "classpath:/application-security.yml")
public class TelegramConfig {
    @Value("${telegramBot.token}")
    private String Token;
    @Value("${telegramBot.chatId}")
    private String chatId;
}
