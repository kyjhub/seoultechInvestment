package com.example.seoultechInvestment.entity;

import com.example.seoultechInvestment.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue // ID 생성을 JPA/Hibernate에 위임함
    @UuidGenerator(style = UuidGenerator.Style.TIME) // Hibernate 6 기반의 v7스타일의 Ordered UUID 생성
    @Column(columnDefinition = "BINARY(16)") // MySQL 성능 최적화를 위해 String이 아닌 Binary로 저장
    private UUID id;  // = 데베pk = 계정id
    @Column(unique = true, nullable = false)
    private Long stId;
    private String name;
    private String Department;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    //    private String nickname; //닉네임은 나중에 필요할 때 추가하자.
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String stEmail;

    // 사용자가 보유한 주식 리스트
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Holding> holdings = new ArrayList<>();

    // Role을 string으로 넘겨주는거 유의!!!!
    @Builder
    Member(Long stId, String name, String Department, String password,
           String role, String stEmail) {
        this.stId = stId;
        this.stEmail = stEmail;
        this.name = name;
        this.Department = Department;
        this.password = password;
        this.role = Role.valueOf(role);
    }
}
