# 📈 stInvestment (서울과기대 투자 정보 공유 서비스)

## 📱 Service Screens


### 1️⃣ Authentication (회원가입 및 인증)

| **메인 화면** | **로그인** |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/086cd397-20df-4bb5-83b9-5698d2e87861" width="100%"> | <img src="https://github.com/user-attachments/assets/c5ab357c-03bb-4433-8c6b-cbb467cda44e"> |
| **메인 화면** | **로그인** |

| **이메일 인증** |
| :---: |
| <img src="https://github.com/user-attachments/assets/821779e8-6594-462d-8331-d30b1e3b1ece"> |
| **학교 이메일 인증번호 발송 및 검증** |

<br/>

### 2️⃣ Admin Features (관리자 기능)
관리자는 직관적인 UI를 통해 투자 정보를 손쉽게 등록하고 관리할 수 있습니다.

| **관리자 대시보드** | **투자 종목 등록** |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/96200296-f9f8-4b8f-ada3-e6b53c3d66c4" width="100%"> | <img src="https://github.com/user-attachments/assets/1f4a5beb-eb68-4a65-bb65-00101853c341" width="100%"> |
| **관리자 전용 메뉴 접근** | **종목명, 진입가, 목표가 등 예측 정보 등록** |

| **수익률(결과) 등록** |
| :---: |
| <img src="https://github.com/user-attachments/assets/ba5fb739-e93d-4a39-9ec0-b87f01577cb6" width="60%"> |
| **투자가 종료된 종목의 성공/실패 여부 입력** |

<br/>

### 3️⃣ User Features (사용자 조회)
사용자는 관리자가 등록한 정보를 실시간으로 확인하고, 과거 성과를 조회할 수 있습니다.

| **진행 중인 투자 목록** | **과거 성과 목록 (History)** |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/22964415-26d9-4d85-b90f-77cfb47f6f23" width="100%"> | <img src="https://github.com/user-attachments/assets/9fb3f480-4e83-4dad-b4d6-1cac19220417" width="100%"> |
| **현재 유효한 투자 정보와 목표가 확인** | **종료된 투자의 수익률 및 성공 여부 조회** |

<br/>

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

### 📂 Project Structure

이 프로젝트는 **Layered Architecture**를 기반으로 역할과 책임을 명확히 분리하여 설계되었습니다.

```bash
src
├── main
│   ├── java/com/example/seoultechInvestment
│   │   ├── config         # Security, Web MVC 등 프로젝트 전반의 설정 클래스
│   │   ├── controller     # 클라이언트의 요청을 처리하는 Presentation Layer
│   │   ├── DTO            # 계층 간 데이터 교환을 위한 객체 (Request/Response)
│   │   ├── entity         # DB 테이블과 매핑되는 도메인 객체 (JPA Entity)
│   │   ├── Enum           # 프로젝트 내에서 사용되는 상수 집합 (Role, Status 등)
│   │   ├── repository     # DB 접근을 담당하는 Data Access Layer (Spring Data JPA)
│   │   ├── security       # UserDetails, Auth Provider 등 인증/인가 관련 로직
│   │   ├── service        # 핵심 비즈니스 로직을 수행하는 Service Layer
│   │   ├── validator      # 데이터 유효성 검증을 위한 커스텀 로직
│   │   ├── InitDB         # 초기 데이터 세팅을 위한 클래스
│   │   └── Application    # Spring Boot 실행 진입점
│   │
│   └── resources
│       ├── static         # 정적 리소스 파일
│       │   ├── css        # 스타일 시트
│       │   ├── html       # 정적 HTML 페이지
│       │   └── js         # 클라이언트 사이드 스크립트
│       ├── templates      # 뷰 템플릿 파일
│       ├── application.yml          # 프로젝트 핵심 설정 파일
│       └── application-security.yml # 보안 관련 설정 분리
```
<br/>

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
