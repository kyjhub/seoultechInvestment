package com.example.seoultechInvestment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class AccountBalance {
    @Id
    private UUID id; // memberId와 동일한 값을 가짐

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Member의 PK를 이 엔티티의 PK로 매핑함
    @JoinColumn(name = "member_id")
    private Member member;

    private double cashAmount;

    public void withdraw(double amount) {
        this.cashAmount -= amount;
    }

    public void deposit(double amount) {
        this.cashAmount += amount;
    }
}
