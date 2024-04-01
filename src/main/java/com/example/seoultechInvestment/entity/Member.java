package com.example.seoultechInvestment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @NotNull(message ="학번입력은 필수입니다.")
    @Column(unique = true, length = 8)
    private Long stId;
    @NotBlank(message ="이름은 필수입니다.")
    private String name;
    @NotBlank(message ="학과는 필수입니다.")
    private String Department;
    //학교 이메일도 필요
    private String stGalleryNickname; //cam empty

    @Builder
    Member(Long stId, String name, String Department, String stGalleryNickname) {
        this.stId = stId;
        this.name = name;
        this.Department = Department;
        this.stGalleryNickname = stGalleryNickname;
    }
}
