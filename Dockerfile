# 1. OpenJDK 이미지 베이스로 설정
FROM openjdk:17-jdk-slim

WORKDIR /app

# 2. 애플리케이션 JAR 파일 복사
COPY target/bank-0.0.1-SNAPSHOT.jar /app.jar
# 3. 환경 변수 설정 (DB 관련 정보)
ENV SPRING_DATASOURCE_URL=jdbc:mysql://gu9duck.cbmgicamgfgg.ap-northeast-1.rds.amazonaws.com:3306/account
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=guguduck

# 4. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 필요 시 포트 설정 (Spring Boot 기본 포트: 8080)
EXPOSE 8080
