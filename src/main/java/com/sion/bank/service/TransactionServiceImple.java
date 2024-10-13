package com.sion.bank.service;

import com.sion.bank.model.Account;
import com.sion.bank.model.Transaction;
import com.sion.bank.model.TransactionType;
import com.sion.bank.repository.AccountRepository;
import com.sion.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImple implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    public void  createTransaction(Long fromAccountID, Long toAccountID, BigDecimal amount, TransactionType transactionType) {
        // 입금 계좌(B)
        Account toAccount = accountRepository.findById(toAccountID)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        // 출금 계좌(A)
        Account fromAccount = accountRepository.findById(fromAccountID)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        // 출금 계좌 잔액 확인
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in source account");
        }


        // 출금 계좌(A)의 잔액 차감
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        BigDecimal fromAccountBalance = fromAccount.getBalance();

        // 입금 계좌(B)의 잔액 추가
        toAccount.setBalance(toAccount.getBalance().add(amount));
        BigDecimal toAccountBalance = toAccount.getBalance();

        // 트랜잭션 생성 및 저장 (출금, 입금 모두 같은 트랜잭션으로 처리)
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setTransactionType(transactionType); //TRANSFER로 고정 됨
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setBalanceAfterTransaction(fromAccountBalance);
        transaction.setFromAccountBalanceAfter(fromAccountBalance);
        transaction.setToAccountBalanceAfter(toAccountBalance);

        transactionRepository.save(transaction);


    }

    public List<Transaction> findAllRelevantTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAllRelevantTransactionsByAccountId(accountId);
    }
//    public List<Transaction> findAllTransactionsByAccountId(Long fromAccountID) {
//        // 해당 계좌의 모든 거래 내역을 최근순으로 반환
//        return transactionRepository.findAllTransactionsByFromAccountId(fromAccountID);
//    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}