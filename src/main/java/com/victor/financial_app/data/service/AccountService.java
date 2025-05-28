package com.victor.financial_app.data.service;

import com.victor.financial_app.data.repository.AccountRepository;
import com.victor.financial_app.data.repository.AccountStockRepository;
import com.victor.financial_app.data.repository.StockRepository;
import com.victor.financial_app.dtos.account.AccountStockDTO;
import com.victor.financial_app.dtos.account.AccountStockResponseDTO;
import com.victor.financial_app.entity.AccountStock;
import com.victor.financial_app.entity.AccountStockId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository,
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }


    public void associate(String accountId, AccountStockDTO accountStockDTO) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(accountStockDTO.stockId())
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStock());

        var accountStock = new AccountStock(id,
                account, stock, accountStockDTO.quantity()
        );

        accountStockRepository.save(accountStock);
    }

    public AccountStockResponseDTO listStocks(String accountId) {
        return null;
    }
}
