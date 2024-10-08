package com.sion.bank.controller;

import com.sion.bank.model.AccountType;
import com.sion.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public String createAccount(
            @RequestParam("accountName") String accountName,
            @RequestParam("bankName") String bankName,
            @RequestParam("accountType") AccountType accountType,
            @RequestParam(value = "initialBalance", defaultValue = "0.00") BigDecimal initialBalance,
            Model model) {


        try{
            accountService.createAccount(
                    accountName,
                    bankName,
                    accountType,
                    initialBalance);

            return "redirect:/home";  // 계좌 생성 후 리다이렉트
        }catch ( Exception e ) {
            model.addAttribute("error", e.getMessage());
            return "/all-functions";  // 회원가입 페이지로 다시 이동
        }

    }
}