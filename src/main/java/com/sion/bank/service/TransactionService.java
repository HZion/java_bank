package com.sion.bank.service;

import com.sion.bank.model.Transaction;
import com.sion.bank.model.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void createTransaction(Long toAccountID, Long fromAccountID, BigDecimal amount, TransactionType transactionType) ;
//    List<Transaction> findAllTransactionsByAccountId(Long fromAccountID);

     List<Transaction> findAllRelevantTransactionsByAccountId(Long accountId);

}
