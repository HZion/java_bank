package com.sion.bank.config;


import com.sion.bank.model.Account;
import com.sion.bank.model.User;
import com.sion.bank.service.AccountService;
import com.sion.bank.service.UserService;
import com.sion.bank.service.UserServiceImple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;
import java.util.List;
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String sessionId = request.getSession().getId();
        String username = authentication.getName();

        User user = userService.getUserByUsername(username);
        // 세션 정보를 Redis에 저장
        redisTemplate.opsForHash().put(sessionId, "username", username);
        redisTemplate.opsForHash().put(sessionId, "authorities", authentication.getAuthorities().toString());
        redisTemplate.opsForHash().put(sessionId, "userId", user.getId());
        System.out.println("세션 ID: " + sessionId + " 유저네임: " + username +"유저id:"+user.getId() +"가 Redis에 저장되었습니다.");

        // 홈 페이지로 리다이렉트
        response.sendRedirect("/home");
    }
}