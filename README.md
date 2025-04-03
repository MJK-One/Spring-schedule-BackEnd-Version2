# Spring-schedule-BackEnd-Version2
**JPA Auditing을 활용한 일정 관리 및 사용자 인증 시스템:** Spring Boot 기반으로 JPA Auditing을 통해 생성/수정 시간을 자동 관리하고, Cookie/Session 기반의 로그인 기능을 제공하여 사용자 인증을 구현한 일정 관리 애플리케이션입니다.

# 주요기능
- **일정 CRUD:** 일정 생성, 조회, 수정, 삭제 기능 제공 <br>
- **사용자 CRUD:** 사용자 생성, 조회, 수정, 삭제 기능 제공 <br>
- **회원 가입:** 사용자 계정 생성 기능 (비밀번호 포함) <br>
- **로그인 (인증):** Cookie/Session 기반의 사용자 인증 기능 (이메일, 비밀번호 사용) <br>
- **일정 페이징 조회:**
  - Spring Data JPA의 Pageable과 Page 인터페이스를 활용한 페이지네이션
  - 제공할일 제목, 할일 내용, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드 조회
 
# 학습 포인트
- **JPA Auditing:** 엔티티의 생성/수정 시간 자동 관리 메커니즘 이해 및 활용
- **JPA 연관 관계 매핑:** 일정과 댓글 간의 연관 관계 설정 및 활용 방법 학습 (OneToMany, ManyToOne 등)
- **Spring Data JPA:** JPA를 더 쉽게 사용할 수 있도록 Spring에서 제공하는 기능 학습 (Repository 인터페이스, Pageable, Page 등)
- **Cookie/Session 기반 인증:** HTTP 기반 인증 방식 이해 및 구현
- **필터 (Filter):** 요청/응답 가로채기 및 전/후 처리 방식 이해 및 활용 (인증 처리)
- **암호화:** PasswordEncoder 사용하여 사용자 비밀번호 암호화
- **페이징 (Pagination):** 대량의 데이터를 효율적으로 관리하고 사용자 경험을 향상시키는 방법 학습 (offset/limit, Pageable/Page 인터페이스 활용)
- **예외 처리:** 발생 가능한 예외 상황에 대한 적절한 처리 및 HTTP 상태 코드 반환

# 회고록 : 맛있는 성장의 기록 😋
> JPA Auditing은 국밥과 같았습니다. 엔티티의 생성/수정 시간을 자동으로 관리해주는 덕분에 개발 과정이 간단하고, 데이터의 신뢰도를 높여 든든합니다. <br>
> Spring Data JPA는 비빔밥과 같았습니다. 여러가지의 복잡한 데이터베이스 작업을 훨씬 간편하게 만들어주었고, Repository 인터페이스를 활용하면서 개발 생산성을 크게 향상시킬 수 있었습니다. <br>

# 보안해야할 점
### 사용자 권한 관리: 데이터 관리의 효율성을 높이는 핵심 기능 🔑
사용자 테이블에 관리자 속성을 도입하여 시스템 관리의 효율성을 극대화하고, 각 사용자는 true 또는 false 값을 통해 관리자 권한을 부여받을 수 있으며, 로그인 시 해당 권한을 확인하여 접근 가능한 기능 범위를 제어할 수 있게 하면 좋을 듯 하다.

<hr>

# API 명세서
## 1. 사용자 API

| 메서드 | URL                     | 설명     | 요청 본문 예시                                                                                                                                                                                                    |
| :----- | :----------------------- | :------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| POST   | `/users/login`           | 로그인   | ```json { "email" : "example@exam.com", "password" : "password123!" }```                                                                                                                                       |
| POST   | `/users/signup`          | 회원가입 | ```json { "username": "홍길동", "email": "example@exam.com", "password": "password123!" }```                                                                                                                          |
| GET    | `/users/{회원ID}`         | 회원 조회 | -                                                                                                                                                                                                                 |
| PUT    | `/users/password/{회원ID}` | 비밀번호 변경 | ```json { "oldPassword": "password123!", "newPassword": "newPassword456!" }```                                                                                                                                      |
| DELETE | `/users/delete/{회원ID}`   | 회원 탈퇴 | ```json { "email" : "example@exam.com", "password" : "password123!" }```                                                                                                                                               |

## 2. 일정 관리 API

| 메서드 | URL                 | 설명     | 요청 본문 예시                                                                                                                                                                                                    |
| :----- | :------------------- | :------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| POST   | `/schedules/{회원ID}` | 일정 추가 | ```json { "title" : "TodoList", "content" : "TodoList Cotent" }```                                                                                                                                                   |
| GET    | `/schedules/{회원ID}` | 일정 확인 | ```json { "id" : "1" }```                                                                                                                                                   |
| PUT    | `/schedules/{회원ID}` | 일정 변경 | ```json { "id" : "1", "newTitle": "TodoList Update", "newContent": "TodoList Content Update" }```                                                                                                                                                   |
| DELETE | `/schedules/{회원ID}` | 일정 삭제 | ```json { "id" : "1", "password":"password123!" }```                                                                                                                                                                 |
# ERD
![캡처](https://github.com/user-attachments/assets/4be0eb8e-07ac-4e2e-bd3d-64cc16603b20)

## User 테이블
- userId: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT)
- username: 사용자 이름, 비어 있을 수 없음 (NOT NULL)
- email: 고유한 이메일 주소 (UNIQUE), 비어 있을 수 없음 (NOT NULL)
- password: 비밀번호, 비어 있을 수 없음 (NOT NULL)
- createdAt: 레코드 생성 시간, 기본값은 현재 시간 (DEFAULT CURRENT_TIMESTAMP)
- updatedAt: 레코드 업데이트 시간, 기본값은 현재 시간 (DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP), 수정 시 자동 업데이트
<BR>

## Schedule 테이블
- id: 기본키 (Primary Key), 자동 증가 (AUTO_INCREMENT)
- userId: User 테이블의 userId를 참조하는 외래키 (Foreign Key)
- title: 일정 제목, 비어 있을 수 없음 (NOT NULL)
- content: 일정 내용
- createdAt: 레코드 생성 시간, 기본값은 현재 시간 (DEFAULT CURRENT_TIMESTAMP)
- updatedAt: 레코드 업데이트 시간, 기본값은 현재 시간 (DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP), 수정 시 자동 업데이트
<BR>

## 관계
- User와 Schedule 테이블 간의 관계:
  - 1:N 관계 (한 명의 사용자(User)는 여러 일정(Schedule)을 가질 수 있음)
  - Schedule.userId는 User.userId를 외래키로 참조하며, 사용자가 삭제되면 해당 사용자와 관련된 모든 일정도 삭제됨 (ON DELETE CASCADE).

# SQL Schema
```
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    content TEXT,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP,
    updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES User(userId) ON DELETE CASCADE;
);
```
