package com.sion.bank.controller;
import com.sion.bank.model.Account;
import com.sion.bank.model.User;
import com.sion.bank.service.AccountService;
import com.sion.bank.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RouterController {

//    private final UserService userService;
//
//    private final AccountService accountService;
////
//    @Autowired
//    public RouterController(UserService userService, AccountService accountService) {
//        this.userService = userService;
//        this.accountService = accountService;
//    }

//    @GetMapping("/")
//    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
//        if (userDetails != null) {
//            String username = userDetails.getUsername();
//            model.addAttribute("username", username);
//            model.addAttribute("accounts", accountService.getUserAccounts(userService.getUserByUsername(username)));
//        }
//        return "home";
//    }
@GetMapping("/login")
public String login() {

    return "login";

}
@GetMapping("/")
public String index() {

        return "home";

    }

}