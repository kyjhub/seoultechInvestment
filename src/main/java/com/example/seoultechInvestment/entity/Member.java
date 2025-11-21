package com.example.seoultechInvestment.entity;

import com.example.seoultechInvestment.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    private Long stId;  // = 데베pk = 계정id
    private String name;
    private String Department;
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
//    private String nickname; //닉네임은 나중에 필요할 때 추가하자.
    private String password;
    private String stEmail;

    // Role을 string으로 넘겨주는거 유의!!!!
    @Builder
    Member(Long stId, String name, String Department, String password,
           String role, String stEmail) {
        this.stEmail = stEmail;
        this.stId = stId;
        this.name = name;
        this.Department = Department;
        this.password = password;
        this.role = Role.valueOf(role);
    }
}
