package com.victor.financial_app.data.service;

import com.victor.financial_app.data.client.BrapiClient;
import com.victor.financial_app.data.repository.AccountRepository;
import com.victor.financial_app.data.repository.AccountStockRepository;
import com.victor.financial_app.data.repository.StockRepository;
import com.victor.financial_app.dtos.account.AccountStockDTO;
import com.victor.financial_app.dtos.account.AccountStockResponseDTO;
import com.victor.financial_app.entity.AccountStock;
import com.victor.financial_app.entity.AccountStockId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository,
                          StockRepository stockRepository,
                          AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void associate(String accountId, AccountStockDTO accountStockDTO) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(accountStockDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(account.getAccountId(), stock.getStock());

        var accountStock = new AccountStock(id,
                account, stock, accountStockDTO.quantity()
        );

        accountStockRepository.save(accountStock);
    }

    public List<AccountStockResponseDTO> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStockList().stream().map(accountStock ->
                new AccountStockResponseDTO(accountStock.getStock().getStock(),
                accountStock.getQuantity(),
                getTotal(accountStock.getQuantity(), accountStock.getStock().getStock()))).toList();
    }

    private Double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().get(0).regularMarketPrice();

        return price * quantity;
    }
}
