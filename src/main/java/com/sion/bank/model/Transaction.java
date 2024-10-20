package com.sion.bank.model;

import jakarta.persistence.*;
<<<<<<< HEAD

import java.io.Serializable;
=======
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
<<<<<<< HEAD
public class Transaction  implements Serializable {

    private static final long serialVersionUID = 1L;  // 직렬화 버전 ID 추가
=======
public class Transaction {
>>>>>>> 878a0c736e203ece2cc2a3fcf425baf8ee3257aa

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 출금 계좌 (송금 계좌)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    // 입금 계좌 (수신 계좌)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(nullable = false)
    private BigDecimal balanceAfterTransaction;

    @Column(name = "from_account_balance_after")
    private BigDecimal fromAccountBalanceAfter;

    @Column(name = "to_account_balance_after")
    private BigDecimal toAccountBalanceAfter;

    public BigDecimal getFromAccountBalanceAfter() {
        return fromAccountBalanceAfter;
    }

    public void setFromAccountBalanceAfter(BigDecimal fromAccountBalanceAfter) {
        this.fromAccountBalanceAfter = fromAccountBalanceAfter;
    }

    public BigDecimal getToAccountBalanceAfter() {
        return toAccountBalanceAfter;
    }

    public void setToAccountBalanceAfter(BigDecimal toAccountBalanceAfter) {
        this.toAccountBalanceAfter = toAccountBalanceAfter;
    }

    public boolean isWithdrawal(Long accountId) {
        return this.fromAccount.getId().equals(accountId);
    }

    public BigDecimal getBalanceForAccount(Long accountId) {
        if (isWithdrawal(accountId)) {
            return this.balanceAfterTransaction;
        } else {
            return this.toAccount.getBalance();
        }
    }

    public BigDecimal getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public void setBalanceAfterTransaction(BigDecimal balanceAfterTransaction) {
        this.balanceAfterTransaction = balanceAfterTransaction;
    }
    // 생성자
    public Transaction(Long id, Account fromAccount, Account toAccount, TransactionType transactionType, BigDecimal amount, String description, LocalDateTime transactionDate) {
        this.id = id;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.transactionType = transactionType;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public Transaction() {}

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @PrePersist
    protected void onCreate() {
        if (transactionDate == null) {
            transactionDate = LocalDateTime.now();
        }
    }
}
