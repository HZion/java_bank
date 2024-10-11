package com.sion.bank.controller;

import com.sion.bank.model.Account;
import com.sion.bank.model.User;
import com.sion.bank.service.AccountService;
import com.sion.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller

@SessionAttributes({"user", "accounts"})
public class RouterController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String index(HttpSession session, Model model) {
        System.out.println("index");
        // 현재 인증된 사용자 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 ID(username) 가져오기
        String username = authentication.getName();
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof User) {
//            Long userId = ((User) principal).getId();
//            System.out.println("User ID: " + userId);
//        }
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role = authorities.stream().findFirst()
//                .map(GrantedAuthority::getAuthority)
//                .orElse("ROLE_USER");  // 권한이 없으면 기본 권한 설정

        // 세션에서 'user' 객체를 가져옴
        User user = userService.getUserByUsername(username);
        List<Account> accounts = accountService.getUserAccounts(user);

        session.setAttribute("user", user);
        session.setAttribute("accounts", accounts);
        User a = (User) session.getAttribute("user");
        List<Account> b = (List<Account>) session.getAttribute("accounts");

        System.out.println(a);
        System.out.println(b);

        BigDecimal totalBalance = accounts.stream()
                .map(Account::getBalance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 모델에 사용자 및 계좌 정보 추가
        model.addAttribute("username", username);
        model.addAttribute("accounts", accounts);
        model.addAttribute("totalBalance", totalBalance);

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

    @GetMapping("all-functions")
    public String allFunctions(Model model) {
        return "all-functions";
    }

    @GetMapping("/account")
    public String makeAccount(Model model) {
        return  "account";
    }


}
