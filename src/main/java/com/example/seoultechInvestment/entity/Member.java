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
    private Long stId;
    private String name;
    private String Department;
    private String stGalleryNickname; //cam empty
    private String password;
    @Builder
    Member(Long stId, String name, String Department, String stGalleryNickname, String password) {
        this.stId = stId;
        this.name = name;
        this.Department = Department;
        this.stGalleryNickname = stGalleryNickname;
        this.password = password;
    }
}
