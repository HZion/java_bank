package com.sion.bank.controller;

import com.sion.bank.service.UserServiceImple;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sion.bank.model.User;
import com.sion.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("/user/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {

        try {
            User user = userService.loginUser(username, password);

            // 로그인 성공 시 세션에 사용자 정보 저장
            // session.setAttribute("user", user);
            return "redirect:/home";  // 홈 페이지로 리다이렉트
        } catch (RuntimeException e) {

            model.addAttribute("error", "Invalid username or password");
            return "login";  // 로그인 페이지로 다시 이동
        }
    }


    @PostMapping("/user/signup")
    public String signUp(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        logger.info("User registration Controler started for: {}", username);
        try {

            User user = userService.registerUser(username, password);
            return "redirect:/login";  // 회원가입 성공 시 로그인 페이지로 리다이렉트
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";  // 회원가입 페이지로 다시 이동
        }
    }


}