package com.sion.bank.service;

import com.sion.bank.model.Account;

import java.util.List;

public interface redisService {
    String getSessionIdForUser(String userId);
    List<Object> getAccountsForUser(String userId);
    List<Account> getRedisAccountsByUser(String sessionId, String userId );
    void setRedisAccountsByUser(String sessionId ,String userId, List<Account> accounts);
    void cleanup();
    void publishEvent(String channel, String message);
}
