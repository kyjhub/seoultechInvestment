package com.example.seoultechInvestment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @NotBlank(message ="학번입력은 필수입니다.")
    @Column(unique = true, length = 8)
    Long stId;
    @NotBlank(message ="이름은 필수입니다.")
    String name;
    @NotBlank(message ="학과는 필수입니다.")
    String Department;
    String stGalleryNickname; //cam empty
}
