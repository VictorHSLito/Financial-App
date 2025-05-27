package com.victor.financial_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class AccountStockId {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "stock_id")
    private String stockId;

    public AccountStockId(UUID accountId, String stockId) {
        this.accountId = accountId;
        this.stockId = stockId;
    }

    public AccountStockId() {
    }
}
