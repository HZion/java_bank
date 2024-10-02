package com.sion.bank.service;

import com.sion.bank.model.User;
import com.sion.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user) {
        userRepository.save(user);  // 비밀번호 암호화 없이 저장
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registerUser(String username, String password) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return false;
    }
}


