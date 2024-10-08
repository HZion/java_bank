package com.sion.bank.service;

import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;
import com.sion.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImple implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public void AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(User user, String accountName, String bankName, AccountType accountType) {
        Account account = new Account();
        account.setUser(user);
        account.setAccountName(accountName);
        account.setBankName(bankName);
        account.setAccountType(accountType);
        account.setAccountNumber(generateAccountNumber()); // 이 메서드는 별도로 구현해야 합니다.
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUserAndIsActiveTrue(user);
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Override
    @Transactional
    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccountByNumber(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    private String generateAccountNumber() {
        // 계좌번호 생성 로직 구현
        return String.format("%014d", System.currentTimeMillis());
    }
}