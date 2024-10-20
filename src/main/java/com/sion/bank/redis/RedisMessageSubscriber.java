package com.sion.bank.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 메시지 처리
        String channel = new String(pattern);
        String messageBody = message.toString();
        System.out.println("Received message: " + messageBody + " from channel: " + channel);



        if (channel.equals("accountCreation") && messageBody.contains("created")) {
            String[] parts = messageBody.split(" ");
            if (parts.length >= 4) { // 필요한 정보가 모두 포함되어 있는지 확인
                String userId = parts[1];
                String sessionId = parts[2];
                // 캐시 삭제 로직 수행
                Set<String> keys = redisTemplate.keys(sessionId + ":" + userId + ":*");
                if (keys != null && !keys.isEmpty()) {
                    redisTemplate.delete(keys);
                    System.out.println("Cache for account " + userId + " has been cleared.");
                }
            } else {
                System.out.println("잘못된 메시지 형식입니다: " + messageBody);
            }
        }
    }
}