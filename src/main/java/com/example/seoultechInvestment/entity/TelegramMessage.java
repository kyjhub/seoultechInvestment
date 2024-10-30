package com.example.seoultechInvestment.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application-security.yml")
public class TelegramMessage {
    @Value("${telegramBot.token}")
    private String Token;
    @Value("${telegramBot.chatId}")
    private String chatId;
    private String text;

    TelegramMessage(String text) {
        this.text = text;
    }
}
