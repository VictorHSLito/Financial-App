package com.victor.financial_app.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_billing_address")
public class BillingAddress {
    @Id
    @Column(name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress(UUID id, Account account, String street, Integer number) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
    }

    public BillingAddress() {
    }
}
