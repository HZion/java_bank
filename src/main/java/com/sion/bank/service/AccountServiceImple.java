package com.sion.bank.service;

import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;
import com.sion.bank.repository.AccountRepository;
import com.sion.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImple implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createAccount(
                                 String accountName,
                                 String bankName,
                                 AccountType accountType,
                                 BigDecimal balance) {
        
        System.out.println("계좌생성시작");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자의 ID(username) 가져오기
        String username = authentication.getName();
        System.out.println("Logged in username: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));


        Account account = new Account();

        account.setUser(user);
        account.setAccountName(accountName);
        account.setBankName(bankName);
        account.setAccountType(accountType);
        account.setAccountNumber(generateAccountNumber());  // 계좌번호 생성 로직
        account.setBalance(balance);


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

    public static String convertToKorean(String accountType) {
        switch (accountType) {
            case "CHECKING":
                return "입출금통장";
            case "SAVINGS":
                return "저축예금";
            case "MONEY_MARKET":
                return "MMF";
            case "CERTIFICATE_OF_DEPOSIT":
                return "정기예금";
            default:
                return accountType;
        }
    }
}