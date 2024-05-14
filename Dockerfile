# Base image
FROM bellsoft/liberica-openjdk-alpine:17

# 작업 디렉토리 설정
WORKDIR /app

# Gradle Wrapper와 빌드 스크립트 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Gradle Wrapper에 실행 권한 부여
RUN chmod +x ./gradlew

# 종속성 캐싱
RUN ./gradlew dependencies

# 프로젝트의 전체 소스 파일을 복사
COPY src src

# 빌드 수행 및 로그 저장
RUN ./gradlew clean build > build.log || (cat build.log && false)

# 애플리케이션 포트 노출
EXPOSE 8080

# JAR 파일 복사
ARG JAR_FILE=build/libs/*.jar
RUN cp ${JAR_FILE} app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
