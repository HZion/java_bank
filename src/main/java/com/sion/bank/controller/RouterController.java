package com.sion.bank.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class RouterController {


@GetMapping("/login")
public String login() {

    return "login";

}
@GetMapping("/home")
public String index() {

        return "home";

}
@GetMapping("/signup")
    public String signup() {

        return "signup";

    }

}