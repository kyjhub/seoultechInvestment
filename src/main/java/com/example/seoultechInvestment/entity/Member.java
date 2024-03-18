package com.example.seoultechInvestment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Member {
    @Id
    @NotBlank(message ="학번입력은 필수입니다.")
    Long stId;
    @NotBlank(message ="학번입력은 필수입니다.")
    String name;
    @NotBlank(message ="학번입력은 필수입니다.")
    String Department;
    String stGalleryNickname; //cam empty
}
