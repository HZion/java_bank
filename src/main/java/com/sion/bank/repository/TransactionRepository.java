package com.sion.bank.repository;

import com.sion.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :fromAccountID OR t.toAccount.id = :fromAccountID ORDER BY t.transactionDate DESC")
    List<Transaction> findAllTransactionsByFromAccountId(@Param("fromAccountID") Long fromAccountID);

}