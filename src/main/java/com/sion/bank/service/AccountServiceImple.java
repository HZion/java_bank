package com.sion.bank.service;

import com.sion.bank.model.Account;
import com.sion.bank.model.AccountType;
import com.sion.bank.model.User;
import com.sion.bank.repository.AccountRepository;
import com.sion.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private redisService redisService;

    @Override
    @Transactional
    public void createAccount(String accountName,
                                 String bankName,
                                 AccountType accountType,
                                 BigDecimal balance,
                                 String sessionId) {
        
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

        accountRepository.save(account);
        redisService.publishEvent("accountCreation", "Account " + user.getId()+" " + sessionId + " created");


    }

    @Override
    public List<Account> getUserAccounts(User user) {
        return accountRepository.findByUserAndIsActiveTrue(user);
    }
    @Override
    public List<Account> getAccountsByUserId(Long userId){
        return accountRepository.findByUserIdAndIsActiveTrue(userId);
    }





    public Account getAccountByID(Long id){
        try {
            return accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + id));
        } catch (AccountNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    @Override
    public Account getAccountByNumberBank(String accountNumber,String bank){

        try {
            // 계좌를 찾음
            Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);

            // 은행명이 일치하는지 확인
            if (account.isPresent() && account.get().getBankName().equals(bank)) {
                return account.orElse(null); // 일치하는 경우 계좌 반환
            } else {
                // 은행명이 일치하지 않거나 계좌가 없는 경우
                throw new RuntimeException("Account not found or bank name mismatch");
            }
        } catch (Exception e) {
            // 예외 처리 (로그 또는 추가 작업 가능)
            throw new RuntimeException("Account not found");
        }

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