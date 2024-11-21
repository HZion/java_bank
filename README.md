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
<br>
-기능<br>
1.로그인<br>
 -회원가입 기능<br>
 -spring Security Custompage를 이용한 로그인 기능<br>
2.계좌<br>
 - 소유 계좌 출력<br>
 - 특정 계좌 거래 내역 확인<br>
 - 이체 <br>
 - 계좌 생성 기능 <br>
3.주식<br>
 -특정 주식 검색 기능 <br>
<br>

-시연 영상<br>
![ScreenRec_2024-11-19 21-26-49](https://github.com/user-attachments/assets/24ffb99f-0f2f-403e-93b7-17addad2e177)
<br>

-db 테이블<br>
```
create database bank
CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(255) NOT NULL,
roles VARCHAR(100) NOT NULL ,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE accounts (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
user_id BIGINT NOT NULL,
account_number VARCHAR(20) NOT NULL UNIQUE,
account_name VARCHAR(100) NOT NULL,
bank_name VARCHAR(50) NOT NULL,
account_type ENUM('CHECKING', 'SAVINGS', 'MONEY_MARKET', 'CERTIFICATE_OF_DEPOSIT') NOT NULL,
balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
currency VARCHAR(3) NOT NULL DEFAULT 'KRW',
is_active BOOLEAN NOT NULL DEFAULT TRUE,
FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE transactions (
id BIGINT AUTO_INCREMENT PRIMARY KEY,
from_account_id BIGINT,  -- 출금 계좌
to_account_id BIGINT,    -- 입금 계좌
transaction_type ENUM('DEPOSIT', 'WITHDRAWAL', 'TRANSFER') NOT NULL,
amount DECIMAL(15, 2) NOT NULL,
description TEXT,
transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
from_account_balance_after DECIMAL(15, 2),
to_account_balance_after DECIMAL(15, 2),
FOREIGN KEY (from_account_id) REFERENCES accounts(id),
FOREIGN KEY (to_account_id) REFERENCES accounts(id)
);
```

