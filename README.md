앱 포트 8080
redis 6379

쿠버 배포시
```
env:
  - name: SPRING_DATASOURCE_URL
    value: #sql 주소
  - name: SPRING_DATASOURCE_USERNAME
    value: # MySQL 사용자 이름
  - name: SPRING_DATASOURCE_PASSWORD
    value: # MySQL 비밀번호
  - name: REDIS_HOST
    value: #redis 위치
  - name: REDIS_PORT
    value: #redis 포트
  - name: REDIS_PASSWORD
    valueFrom:
      secretKeyRef:
        name: my-redis
        key: redis-password
ports:
  - containerPort: 8080  # gugu-bank 애플리케이션 포트
```

-시연 영상
![ScreenRec_2024-11-19 21-26-49](https://github.com/user-attachments/assets/24ffb99f-0f2f-403e-93b7-17addad2e177)




