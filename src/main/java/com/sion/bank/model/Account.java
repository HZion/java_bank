package com.sion.bank.model;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;  // 직렬화 버전 ID 추가

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User 엔티티와 다대일 관계 설정, 외래 키는 'user_id'
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // 'user_id'가 외래 키가 됨
    private User user;  // User 엔티티 참조

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_name", nullable = false)
    private String accountName;

    @Column(name = "bank_name", nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET utf8mb4")
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String currency;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 생성자
    public Account(Long id, User user, String accountNumber, String accountName, String bankName, AccountType accountType, BigDecimal balance, String currency, boolean isActive) {
        this.id = id;
        this.user = user;  // User를 설정
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.bankName = bankName;
        this.accountType = accountType;
        this.balance = balance;
        this.currency = currency;
        this.isActive = isActive;
//
    }

    public Account() {}

    // Getter 및 Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }




    @PrePersist
    protected void onCreate() {
        if (currency == null) {
            currency = "KRW";
        }
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
        isActive = true;
    }


}
