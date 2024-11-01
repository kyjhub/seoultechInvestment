package com.example.seoultechInvestment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@NoArgsConstructor
@PropertySource("classpath:/application-security.yml")
//@ConfigurationProperties
// @ConfigurationProperties랑 PropertySource 중에 뭘 어떻게 사용할지
//고민해봐야함
public class TelegramBot {
    @Value("${telegramBot.token}")
    private String Token;
    @Value("${telegramBot.chatId}")
    private String chatId;
}
