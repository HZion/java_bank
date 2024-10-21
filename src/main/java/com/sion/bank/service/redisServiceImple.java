package com.sion.bank.service;

import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class redisServiceImple implements redisService {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    public void SessionService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public redisServiceImple(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 사용자 ID와 세션 ID를 Redis에 저장하는 메서드
    public void storeSession(HttpSession session, Authentication authentication) {
        // 세션 ID 가져오기
        String sessionId = session.getId();

        // 현재 인증된 사용자 ID 가져오기
        String userId = authentication.getName();

        // 사용자 ID와 세션 ID를 Redis에 저장
        redisTemplate.opsForValue().set(userId, sessionId);
        // 사용자 계좌 목록을 Redis에 저장

        System.out.println("Stored sessionId: " + sessionId + " and account list for userId: " + userId);

    }

    // 사용자 ID로 세션 ID 조회
    public String getSessionIdForUser(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    public List<Object> getAccountsForUser(String userId) {
        // 모든 계좌 정보를 조회
        return Collections.singletonList(redisTemplate.opsForList().range(userId + ":accounts", 0, -1));
    }

    public List<Account> getRedisAccountsByUser(String sessionId, String userId ) {
        List<Account> accounts = new ArrayList<>();

        for (String accountKey : redisTemplate.keys(sessionId +":"+userId +":*")) {
            Account account = new Account();

            account.setId(Long.parseLong((String) redisTemplate.opsForHash().get(accountKey, "id")));
            account.setUser(userService.getUserByUsername((String) redisTemplate.opsForHash().get(accountKey, "user"))); // User 객체를 설정
            account.setAccountNumber((String) redisTemplate.opsForHash().get(accountKey, "accountNumber"));
            account.setAccountName((String) redisTemplate.opsForHash().get(accountKey, "accountName"));
            account.setBankName((String) redisTemplate.opsForHash().get(accountKey, "bankName"));
            account.setAccountType(AccountType.valueOf((String) redisTemplate.opsForHash().get(accountKey, "accountType")));
            account.setBalance(new BigDecimal((String) redisTemplate.opsForHash().get(accountKey, "balance")));
            account.setCurrency((String) redisTemplate.opsForHash().get(accountKey, "currency"));
            account.setActive(Boolean.parseBoolean((String) redisTemplate.opsForHash().get(accountKey, "isActive")));
            accounts.add(account);
        }

        return accounts;
    }

    public  void setRedisAccountsByUser(String sessionId ,String userId, List<Account> accounts) {

        for (Account account : accounts) {
            String accountKey = sessionId + ":"+ userId+ ":" + account.getId(); // 고유한 키 생성
            redisTemplate.opsForHash().put(accountKey, "id", account.getId().toString());
            redisTemplate.opsForHash().put(accountKey, "user", account.getUser().getUsername()); // 필요한 경우 사용자 이름 저장
            redisTemplate.opsForHash().put(accountKey, "accountNumber", account.getAccountNumber());
            redisTemplate.opsForHash().put(accountKey, "accountName", account.getAccountName());
            redisTemplate.opsForHash().put(accountKey, "bankName", account.getBankName());
            redisTemplate.opsForHash().put(accountKey, "accountType", account.getAccountType().name());
            redisTemplate.opsForHash().put(accountKey, "balance", account.getBalance().toString());
            redisTemplate.opsForHash().put(accountKey, "currency", account.getCurrency());
            redisTemplate.opsForHash().put(accountKey, "isActive", String.valueOf(account.isActive()));
            redisTemplate.expire(accountKey, 10, TimeUnit.MINUTES);
        }
    }

    @PreDestroy
    public void cleanup() {
        // Redis에서 모든 세션 데이터 삭제
        redisTemplate.delete("spring:session:sessions");
        redisTemplate.delete("spring:session:expirations");

        System.out.println("Session data cleared from Redis on shutdown.");
    }

    public void publishEvent(String channel, String message) {
        redisTemplate.convertAndSend(channel, message);
    }
}