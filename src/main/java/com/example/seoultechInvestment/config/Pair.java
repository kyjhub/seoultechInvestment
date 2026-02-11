package com.example.seoultechInvestment.config;

import java.util.UUID;

// 튜플 자료형을 위한 클래스
public class Pair {

    // holding entity를 위한 자료형
    public record UuidLongPair(UUID uuid, Long value) {}
}
