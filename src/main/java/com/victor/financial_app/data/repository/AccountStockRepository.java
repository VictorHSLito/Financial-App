package com.victor.financial_app.data.repository;

import com.victor.financial_app.entity.AccountStock;
import com.victor.financial_app.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
