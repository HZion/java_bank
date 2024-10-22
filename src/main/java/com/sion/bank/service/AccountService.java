package com.sion.bank.service;


import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void createAccount( String accountName, String bankName, AccountType accountType, BigDecimal balance, String seesionID);
    List<Account> getUserAccounts(User user);
    Account getAccountByNumber(String accountNumber);
    Account getAccountByNumberBank(String accountNumber,String bank);
    Account getAccountByID(Long id);
<<<<<<< HEAD
    List<Account> getAccountsByUserId(Long userId);
=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
    void deposit(String accountNumber, BigDecimal amount);
    void withdraw(String accountNumber, BigDecimal amount);

}