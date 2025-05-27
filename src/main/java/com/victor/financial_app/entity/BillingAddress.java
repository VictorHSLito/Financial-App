package com.victor.financial_app.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_address")
public class BillingAddress {
    @Id
    @Column(name = "billing_address_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account; // Esse é o nome que deve ser passado no MappedBy

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress(Account account, String street, Integer number) {
        this.account = account;
        this.street = street;
        this.number = number;
    }

    public BillingAddress() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
