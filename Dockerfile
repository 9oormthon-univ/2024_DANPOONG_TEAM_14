# 빌드 단계: Gradle 이미지 사용하여 Spring Boot 빌드
FROM gradle:8.10.1-jdk17 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 스크립트와 소스 코드 복사
COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon || return 0
COPY src ./src

# Gradle 빌드 실행
RUN gradle build --no-daemon

# 실행 단계: 경량화된 JDK 17 이미지로 애플리케이션 실행
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 단계에서 생성된 JAR 파일을 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 애플리케이션이 사용하는 포트 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]