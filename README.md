# Swap_BE

# 소개

이 프로젝트는 Spring Boot를 기반으로 한 웹 애플리케이션입니다. 아래는 자바 버전 및 사용된 주요 라이브러리와 그 설명입니다.

## Java 버전: 17

- 그룹: team_pwp
- 패키징: jar
- 이름: swap

## 사용된 라이브러리

### Spring Boot Starter Web

- 의존성: implementation 'org.springframework.boot:spring-boot-starter-web'
- 설명: Spring MVC와 임베디드 톰캣을 포함하여 웹 애플리케이션 개발을 간편하게 해줍니다.

### Lombok

- 의존성:
  compileOnly 'org.projectlombok:lombok'
  annotationProcessor 'org.projectlombok:lombok'
- 설명: 반복되는 코드를 줄이기 위해 사용하는 라이브러리로, 어노테이션을 통해 Getter, Setter, Constructor 등을 자동으로 생성해줍니다.

### H2 Database

- 의존성: runtimeOnly 'com.h2database:h2'
- 설명: 테스트 및 개발용으로 많이 사용되는 인메모리 데이터베이스입니다.

### MySQL Connector/J

- 의존성: runtimeOnly 'com.mysql:mysql-connector-j'
- 설명: MySQL 데이터베이스와의 연결을 지원하는 JDBC 드라이버입니다.

### Spring Boot Starter Test

- 의존성: testImplementation 'org.springframework.boot:spring-boot-starter-test'
- 설명: JUnit, Hamcrest, Mockito 등을 포함한 테스트 도구 모음입니다.

### Spring REST Docs MockMvc

- 의존성: testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
- 설명: Spring REST Docs와 MockMvc를 사용하여 RESTful API 문서를 생성하는 도구입니다.

### JJWT (Java JSON Web Token)

- 의존성:
    - implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    - implementation 'io.jsonwebtoken:jjwt-impl:0.11.5'
    - implementation 'io.jsonwebtoken:jjwt-jackson:0.11.5'
- 설명: JWT (JSON Web Token) 생성 및 검증을 위한 라이브러리입니다.

### Spring Boot Starter Security

- 의존성: implementation 'org.springframework.boot:spring-boot-starter-security'
- 설명: Spring Security를 사용하여 애플리케이션 보안을 설정합니다.

### SpringDoc OpenAPI Starter WebMVC UI

- 의존성: implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0'
- 설명: Swagger UI를 통해 OpenAPI 3 문서를 자동으로 생성해주는 라이브러리입니다.

### Spring Security Test

- 의존성: testImplementation 'org.springframework.security:spring-security-test'
- 설명: Spring Security와 통합된 테스트 도구입니다.

### Spring Boot Starter Data JPA

- 의존성: implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
- 설명: JPA와 Hibernate를 사용하여 데이터베이스 연동을 쉽게 해줍니다.

### Spring Boot Starter WebSocket

- 의존성:
    - implementation 'org.springframework.boot:spring-boot-starter-websocket'
    - implementation 'org.springframework:spring-messaging'
- 설명: WebSocket 통신을 지원하며, 실시간 양방향 통신을 구현할 수 있게 해줍니다.

### Spring Boot Starter Validation

- 의존성: implementation 'org.springframework.boot:spring-boot-starter-validation'
- 설명: Spring Validation을 사용하여 입력 값의 유효성을 검사합니다.

### Spring Cloud AWS Starter

- 의존성: implementation 'org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE'
- 설명: AWS S3와 같은 AWS 서비스와의 통합을 지원하는 라이브러리입니다.


