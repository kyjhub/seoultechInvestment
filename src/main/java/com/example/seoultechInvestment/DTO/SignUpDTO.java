package com.example.seoultechInvestment.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data //noArgsConstructor, RequiredConstructor, getter, setter
        //모두 존재
public class SignUpDTO {
    @NotNull(message ="학번입력은 필수입니다.") //이런 조건은 폼에만 엔티티에는 하지마라
    @Column(unique = true, length = 8)
    private Long stId;
    @NotBlank(message ="이름은 필수입니다.")
    private String name;
    @NotBlank(message ="학과는 필수입니다.")
    private String Department;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
    //학교 이메일도 필요
    private String stGalleryNickname; //cam empty


}
