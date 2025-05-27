package com.victor.financial_app.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id")
    private UUID account_id;

    @Column(name = "description")
    private String description;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account")
    private BillingAddress billingAddress;

    @OneToMany(mappedBy = "account")
    private List<AccountStock> accountStockList;

    public Account(UUID account_id, String description, User user, BillingAddress billingAddress, List<AccountStock> accountStockList) {
        this.account_id = account_id;
        this.description = description;
        this.user = user;
        this.billingAddress = billingAddress;
        this.accountStockList = accountStockList;
    }

    public Account() {
    }

    public UUID getAccount_id() {
        return account_id;
    }

    public void setAccount_id(UUID account_id) {
        this.account_id = account_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<AccountStock> getAccountStockList() {
        return accountStockList;
    }

    public void setAccountStockList(List<AccountStock> accountStockList) {
        this.accountStockList = accountStockList;
    }
}
