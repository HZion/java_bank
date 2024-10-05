package com.sion.bank.service;

import com.sion.bank.model.User;


public interface UserService {
    User registerUser(String username, String password);
    User getUserByUsername(String username);
    boolean isUsernameAvailable(String username);
    User loginUser(String username, String password);
}