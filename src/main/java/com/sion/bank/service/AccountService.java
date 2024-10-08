package com.sion.bank.service;


import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account createAccount( String accountName, String bankName, AccountType accountType, BigDecimal balance);
    List<Account> getUserAccounts(User user);
    Account getAccountByNumber(String accountNumber);
    void deposit(String accountNumber, BigDecimal amount);
    void withdraw(String accountNumber, BigDecimal amount);
}