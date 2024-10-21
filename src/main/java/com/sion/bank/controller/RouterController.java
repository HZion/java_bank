package com.sion.bank.controller;

import com.sion.bank.model.Account;

import com.sion.bank.model.User;
import com.sion.bank.service.AccountService;
import com.sion.bank.service.UserService;

import com.sion.bank.service.redisServiceImple;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.ArrayList;

import java.util.List;

@Controller

@SessionAttributes({"user", "accounts"})
public class RouterController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private redisServiceImple redis;

    private List<Account> accounts = null;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // /home으로 리다이렉트
    }

    @GetMapping("/home")
    public String index(HttpSession session, Model model) {

       // 현재 인증된 사용자 정보를 가져옴

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String sessionId = session.getId();

        // redis를 세션으로 이용한경우
        int userId = (int) redisTemplate.opsForHash().get(sessionId, "userId");

        System.out.println("세션에서 가져올까요?");
        System.out.println("유저ID:"+userId);

        // 1. redis에서 계좌정보가 있는지 확인
        accounts = redis.getRedisAccountsByUser(sessionId, String.valueOf(userId));

        if (accounts == null) {
            accounts = new ArrayList<>();
        }

        if (accounts == null ||accounts.isEmpty()) {
            //2. redis에 계좌 정보가 없는경우

            System.out.println("DB에서 계좌 정보 가져오는 중...");
            //
            //2.1. DB에서 유저id를 기반으로  계좌 정보 가져오기
            accounts = accountService.getAccountsByUserId((long) userId);

            // 2.2 Redis에 sessionId:userId:accountId 로 계좌 정보 저장
            redis.setRedisAccountsByUser(sessionId,String.valueOf(userId),accounts);

        } else {       // DB에서 유저 정보 가져오기
            //3 redis에 계좌 정보가 있는경우
            System.out.println("Redis에서 계좌 정보 가져오기...");
            accounts.clear();
            accounts = accountService.getAccountsByUserId((long) userId);


        }

        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
        // 모델에 사용자 및 계좌 정보 추가
        model.addAttribute("username", username);
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);
        // 사용자의 ID(username) 가져오기
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof User) {
//            Long userId = ((User) principal).getId();
//            System.out.println("User ID: " + userId);
//        }
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role = authorities.stream().findFirst()
//                .map(GrantedAuthority::getAuthority)
//                .orElse("ROLE_USER");  // 권한이 없으면 기본 권한 설정
//        redis.getAccountsForUser()

        // 기존방식 1
//        User user = userService.getUserByUsername(username);
//        List<Account> accounts = accountService.getUserAccounts(user);
//
//        session.setAttribute("user", user);
//        session.setAttribute("accounts", accounts);
//        User a = (User) session.getAttribute("user");
//        List<Account> b = (List<Account>) session.getAttribute("accounts");
//
//        System.out.println(a);
//        System.out.println(b);
//
//        BigDecimal totalBalance = accounts.stream()
//                .map(Account::getBalance)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // 모델에 사용자 및 계좌 정보 추가
//        model.addAttribute("username", username);
//        model.addAttribute("accounts", accounts);
//        model.addAttribute("totalBalance", totalBalance);

        // 세션에서 'user' 객체를 가져옴
//        User user = userService.getUserByUsername(username);
//        List<Account> accounts = accountService.getUserAccounts(user);
//
//        session.setAttribute("user", user);
//        session.setAttribute("accounts", accounts);
//        User a = (User) session.getAttribute("user");
//        List<Account> b = (List<Account>) session.getAttribute("accounts");
//
//        System.out.println(a);
//        System.out.println(b);
//
//        BigDecimal totalBalance = accounts.stream()
//                .map(Account::getBalance)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        // 모델에 사용자 및 계좌 정보 추가
//        model.addAttribute("username", username);
//        model.addAttribute("accounts", accounts);
//        model.addAttribute("totalBalance", totalBalance);


        return "home";
    }
//    //세션이용해서 출력해보자
//@GetMapping("/home")
//public String index(HttpSession session, Model model) {
//    System.out.println("index");
//    User user = (User) session.getAttribute("user");
//    List<Account> accounts = (List<Account>) session.getAttribute("accounts");
//    session.setAttribute("user", user);
//    session.setAttribute("accounts", accounts);
//
//
//    System.out.println(user);
//    System.out.println(accounts);
//
//    BigDecimal totalBalance = accounts.stream()
//            .map(Account::getBalance)
//            .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//    // 모델에 사용자 및 계좌 정보 추가
//    model.addAttribute("username", user.getUsername());
//    model.addAttribute("accounts", accounts);
//    model.addAttribute("totalBalance", totalBalance);
//
//    return "home";
//}


    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/all-functions")
    public String allFunctions(Model model) {
        return "all-functions";
    }


    @PostMapping("/all-functions")
    public String allFunctions_post(Model model) {
        return "all-functions";
    }



    @GetMapping("/account")
    public String makeAccount(Model model) {
        return  "account";
    }



}
