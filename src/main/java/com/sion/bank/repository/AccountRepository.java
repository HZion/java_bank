package com.sion.bank.repository;

import com.sion.bank.model.Account;
import com.sion.bank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
    List<Account> findByUserAndIsActiveTrue(User user);
    Optional<Account> findByAccountNumber(String accountNumber);
//    Account findByAccountID(Long id);
    boolean existsByAccountNumber(String accountNumber);
}