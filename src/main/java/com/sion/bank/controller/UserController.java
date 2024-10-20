package com.sion.bank.controller;

import com.sion.bank.model.Account;
import com.sion.bank.service.AccountService;
<<<<<<< HEAD
import com.sion.bank.service.redisServiceImple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
=======
import com.sion.bank.service.UserServiceImple;
import jakarta.servlet.http.HttpSession;
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.sion.bank.model.User;
import com.sion.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import java.util.List;

@Controller
public class UserController{

<<<<<<< HEAD
=======
    private final UserService userService;
    private final AccountService accountService;
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


<<<<<<< HEAD
    private final UserService userService;
    private final AccountService accountService;
    private final redisServiceImple redisServiceImple;
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
=======
    @Autowired
    public UserController(UserService userService, AccountService accountService, HttpSession session) {
        this.userService = userService;
        this.accountService = accountService;
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa


    @Autowired
    public UserController(UserService userService, AccountService accountService, redisServiceImple redisServiceImple) {
        this.userService = userService;
        this.accountService = accountService;
        this.redisServiceImple = redisServiceImple;
    }


<<<<<<< HEAD


    @PostMapping("/user/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
=======
    @PostMapping("/user/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
        try {
            User user = userService.loginUser(username, password);
            
//            세션이용시
//            List<Account> accounts = accountService.getUserAccounts(user);
//            session.setAttribute("user", user);
//            session.setAttribute("accounts", accounts);

<<<<<<< HEAD
//            // 세션 생성 및 사용자 정보 저장
//            session.setAttribute("user", user);



=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
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