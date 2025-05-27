package com.victor.financial_app.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_stock" )
public class Stock {
    @Id
    @Column(name = "stock_id")
    private String stock;

    @Column(name = "description")
    private String description;

    @OneToMany (mappedBy = "stock")
    private List<AccountStock> accountStockList;

    public Stock(String stock, String description) {
        this.stock = stock;
        this.description = description;
    }

    public Stock() {
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
