package com.sion.bank.repository;

import com.sion.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :fromAccountID OR t.toAccount.id = :fromAccountID ORDER BY t.transactionDate DESC")
//    List<Transaction> findAllTransactionsByFromAccountId(@Param("fromAccountID") Long fromAccountID);

//    @Query(value = "SELECT * FROM transactions t WHERE t.from_account_id = :accountId OR t.to_account_id = :accountId ORDER BY t.transaction_date DESC", nativeQuery = true)
//    List<Transaction> findAllTransactionsByFromAccountId(@Param("accountId") Long accountId);
//
//    @Query("SELECT t FROM Transaction t WHERE " +
//            "(t.transactionType = 'TRANSFER' AND t.fromAccount.id = :accountId) OR " +
//            "(t.transactionType = 'DEPOSIT' AND t.toAccount.id = :accountId) " +
//            "ORDER BY t.transactionDate DESC")
//    List<Transaction> findAllRelevantTransactionsByAccountId(@Param("accountId") Long accountId);



    @Query("SELECT t FROM Transaction t WHERE " +
            "t.fromAccount.id = :accountId OR t.toAccount.id = :accountId " +
            "ORDER BY t.transactionDate DESC")
    List<Transaction> findAllRelevantTransactionsByAccountId(@Param("accountId") Long accountId);

}