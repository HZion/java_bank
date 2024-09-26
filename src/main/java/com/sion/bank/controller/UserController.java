package com.sion.bank.controller;

import com.sion.bank.model.User;
import com.sion.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public String home(HttpSession session, Model model) {
        // 세션에서 유저 확인
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("message", "환영합니다, " + username + "님!");
        } else {
            model.addAttribute("message", "로그인 해주세요.");
        }
        return "index";
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        return home(session, model); // 홈 화면으로 리디렉션
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("username", user.getUsername());// 세션에 유저 저장
            model.addAttribute("message", "로그인 성공!");
            return "redirect:/index"; // 로그인 후 홈으로 이동
        }
        model.addAttribute("error", "로그인 실패! 사용자 이름 또는 비밀번호가 잘못되었습니다.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/login"; // 로그아웃 후 로그인 화면으로 리디렉션
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

//    @PostMapping("/signup")
//    public String registerUser(@RequestParam String username, @RequestParam String password) {
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        userService.saveUser(user);
//        return "login";
//    }

    @PostMapping("/signup")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        if (userService.findByUsername(username) != null) {
            return "signup?error";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));  // 비밀번호 암호화
        userService.saveUser(user);

        return "login";
    }

    @GetMapping("/account")
    public String account(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login"; // 로그인하지 않은 상태라면 로그인 페이지로 리디렉션
        }
        model.addAttribute("username", username);
        return "account"; // 로그인된 사용자에게 계정 정보 표시
    }
}
