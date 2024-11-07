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

<로그인 화면><br>
<img src="https://github.com/user-attachments/assets/febe02fb-1a67-4fd3-bd20-eca23f071d97" alt="Example Image" width="500" height="700"><br>
<회원가입 화면><br>
<img src="https://github.com/user-attachments/assets/eae3650a-7fcf-49f6-b144-c1a07efed46b" alt="Example Image" width="500" height="700">
<br>
<초기 홈화면><br>
<img src="https://github.com/user-attachments/assets/38a216dc-e5b8-4a8e-a52e-b3cd4f5734e7" alt="Example Image" width="500" height="700"><br>
<전체 기능 화면><br>
<img src="https://github.com/user-attachments/assets/0ea97019-98e9-4779-8559-a25ff6bb4077" alt="Example Image" width="500" height="700"><br>
<계좌 생성 화면><br>
<img src="https://github.com/user-attachments/assets/0220c004-aa78-4699-ade8-36a95c1eff8c" alt="Example Image" width="500" height="700"><br>
<계좌가 있는경우 홈화면><br>

<img src="https://github.com/user-attachments/assets/c07d33e5-dfca-4c3a-a134-5e02cefdfd60" alt="Example Image" width="500" height="700"><br>
<송금: 계좌 찾기><br>
<img src="https://github.com/user-attachments/assets/9d29f0b0-d944-464d-9ccd-01b7e8ee673b" alt="Example Image" width="500" height="700"><br>
<송금: 금액 확인><br>
보내는 유저 확인 <br>

<img src="https://github.com/user-attachments/assets/c07d33e5-dfca-4c3a-a134-5e02cefdfd60" alt="Example Image" width="500" height="700"><br>
<송금: 송금확인 화면><br>

<img src="https://github.com/user-attachments/assets/95fa7428-9d56-4203-9978-48759b476edd" alt="Example Image" width="500" height="700"><br>
<계좌: 자세한 화면><br>

<img src="https://github.com/user-attachments/assets/645bc752-fb8a-4789-8b94-1ae75142f9cf" alt="Example Image" width="500" height="700"><br>



