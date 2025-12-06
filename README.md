# 📈 stInvestment (서울과기대 투자 정보 공유 서비스)

### 🛠 Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Database:** MySQL (or H2), Spring Data JPA
- **Security:** Spring Security
- **Infra:** Google Cloud Platform (GCP)
- **Tools:** Telegram Bot API, Naver SMTP

### 💡 Key Features
1. **학교 이메일 인증:** Naver SMTP를 활용한 재학생 인증 시스템 구현
2. **투자 정보 알림:** 관리자 글 작성 시 Telegram Bot 자동 알림 전송
3. **투자 정보:** 매수가/목표가 설정 및 과거 이력 조회 기능
4. **클라우드 배포:** GCP 이용

### 🚀 Entity Relationship

[![](https://mermaid.ink/img/pako:eNqdlN9KG0EUxl9lmKsI0ebP6jZ7V5MIYpOKSXtRAmXdHePS7I5MdmltFEQSkCpUUWlaklCrtEZyodZKCvaFMrPv0DObJpWaXti5mll-35zznXNmK9igJsEaJixl6UWm2wUHwcrlnyTn0Nra-DitoNnss3Qun0ln80hDBey_6_iHp3y3wbdPUCiqZccKuOD0dZl0Zjq9gCr9k1yPqVNElonm50Da657zb11_80QqBkTOZRYwxNatkrz-8EPv-y4SzSvePhDNGxSaTY2N4Ff0cvkVZSZI-I8qP9_gl1t-vTuCZLREZPDrHf-wgUKPUpnZ7IOnufTC8Nr1Qf593_-TftmlxsusbstI4vhGbDeR-FzjZ21-VvsXnoTaSzzgkPh5wPcbt9kU9RYhdcNjjDjuPLMMifv1qmh1eucbd7K_1ah7WQgIm9iLhL0AcEaCorUnqheitRvqXW3wLx3Yjd3RBC6GEgl-OkV99yN8gAe2OnAhvoKLGgIbvcv2CNjVWZEMPUN9_L3GCHowDIyYluFa1JFX17dE9wjxzSvxsR2SZW3X_bfdv9I39FJKd0negpYZjMDWlGcZbP-CHzVh9G4LpilMke4gqzxjOVZ5mcjB87dkXYJGH-8g8b7Dr_90BYdxkVkm1lzmkTC2CYMJhyMOelPA7jKxSQHLJ2WSJd0ruVIqZSu685xSe6Bk1CsuY21JL5Xh5K2YkObv1zr8CgNiEpaknuNibUpJBJdgrYJfYy2qRCYSijo5FVEVNZFQ4koYr2ItPjkRUSOqGk-okVgs8VBR1sP4TRAX-JgSiSkqSGLxaBQEUF-Xskz_bxH8NNZ_AaI3kSA?type=png)](https://mermaid.live/edit#pako:eNqdlN9KG0EUxl9lmKsI0ebP6jZ7V5MIYpOKSXtRAmXdHePS7I5MdmltFEQSkCpUUWlaklCrtEZyodZKCvaFMrPv0DObJpWaXti5mll-35zznXNmK9igJsEaJixl6UWm2wUHwcrlnyTn0Nra-DitoNnss3Qun0ln80hDBey_6_iHp3y3wbdPUCiqZccKuOD0dZl0Zjq9gCr9k1yPqVNElonm50Da657zb11_80QqBkTOZRYwxNatkrz-8EPv-y4SzSvePhDNGxSaTY2N4Ff0cvkVZSZI-I8qP9_gl1t-vTuCZLREZPDrHf-wgUKPUpnZ7IOnufTC8Nr1Qf593_-TftmlxsusbstI4vhGbDeR-FzjZ21-VvsXnoTaSzzgkPh5wPcbt9kU9RYhdcNjjDjuPLMMifv1qmh1eucbd7K_1ah7WQgIm9iLhL0AcEaCorUnqheitRvqXW3wLx3Yjd3RBC6GEgl-OkV99yN8gAe2OnAhvoKLGgIbvcv2CNjVWZEMPUN9_L3GCHowDIyYluFa1JFX17dE9wjxzSvxsR2SZW3X_bfdv9I39FJKd0negpYZjMDWlGcZbP-CHzVh9G4LpilMke4gqzxjOVZ5mcjB87dkXYJGH-8g8b7Dr_90BYdxkVkm1lzmkTC2CYMJhyMOelPA7jKxSQHLJ2WSJd0ruVIqZSu685xSe6Bk1CsuY21JL5Xh5K2YkObv1zr8CgNiEpaknuNibUpJBJdgrYJfYy2qRCYSijo5FVEVNZFQ4koYr2ItPjkRUSOqGk-okVgs8VBR1sP4TRAX-JgSiSkqSGLxaBQEUF-Xskz_bxH8NNZ_AaI3kSA)


### 💡 ERD 상세 설명

**1. Member (회원 테이블)**
*   **역할:** 사용자 정보를 저장합니다.
*   **특징:** 관리자(Admin)가 이 테이블에 속하며, `Investment`를 작성하는 주체가 됩니다.

**2. Stock (주식 종목 테이블)**
*   **역할:** 삼성전자, 테슬라 같은 개별 주식 종목 정보를 저장합니다.
*   **특징:** 주식 정보는 고유하며, 여러 투자 정보에서 참조될 수 있습니다.
*   **관계:** 하나의 주식 종목(예: 삼성전자)에 대해 여러 번의 투자 예측(Investment)이 올라올 수 있으므로 **1:N 관계**입니다.

**3. Investment (투자 정보/예측 테이블)**
*   **역할:** 관리자가 작성한 "이 주식을 얼마에 사서 얼마에 팔아라"는 핵심 정보를 저장합니다.
*   **특징:** 이 테이블이 **Stock(어떤 종목인지)**을 **Foreign Key(외래키)**로 가지고 있는 **다대일(N:1)** 구조입니다.

**4. CustomUserDetails (참고)**
*   이 클래스는 DB 테이블로 생성되지 않고, `Member` 엔티티의 정보를 바탕으로 Spring Security가 "현재 로그인한 사람이 누구인지"를 판단할 때 사용하는 객체입니다. 따라서 ERD에는 포함되지 않지만, 논리적으로는 `Member` 테이블과 1:1로 매핑되어 작동합니다.
