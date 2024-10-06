package com.sion.bank.controller;

import com.sion.bank.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class RouterController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String index(Model model) {
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 ID(username) 가져오기
        String username = authentication.getName();

        // 사용자의 권한(ROLE) 가져오기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream().findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");  // 권한이 없으면 기본 권한 설정

        // 사용자 정보와 권한을 Model에 추가하여 View로 전달
        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "home"; // home 템플릿으로 이동
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("all-functions")
    public String allFunctions(Model model) {
        return "all-functions";
    }
}
