package com.sion.bank.service;

import com.sion.bank.model.User;
import com.sion.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);  // 비밀번호 암호화 없이 저장
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
