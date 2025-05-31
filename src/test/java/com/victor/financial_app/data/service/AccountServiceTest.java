package com.victor.financial_app.data.service;

import com.victor.financial_app.data.repository.AccountRepository;
import com.victor.financial_app.data.repository.AccountStockRepository;
import com.victor.financial_app.data.repository.StockRepository;
import com.victor.financial_app.dtos.account.AccountStockDTO;
import com.victor.financial_app.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountStockRepository accountStockRepository;

    @Mock
    private StockRepository stockRepository;

    @Test
    @DisplayName("Should Associate an Existing Account with An Existing Stock SuccessFully")
    void shouldAssociateAnAccountWithAStockSuccessfully() {
        // Arrange

        UUID accountUUID = UUID.randomUUID();

        Account account = new Account("Account Investiment Test", new User("Username",
                "UserEmail@gmail.com",
                "Password!231",
                Instant.now(),
                null), null, null);

        account.setAccountId(accountUUID);

        Stock stock = new Stock("BBDC3", "Banco Bradesco PN");

        AccountStockId accountId = new AccountStockId(account.getAccountId(), stock.getStock());

        AccountStock accountStock = new AccountStock(accountId, account, stock, 10);

        AccountStockDTO dto = new AccountStockDTO(stock.getStock(), accountStock.getQuantity());

        doReturn(Optional.of(account)).when(accountRepository).findById(account.getAccountId());
        doReturn(Optional.of(stock)).when(stockRepository).findById(stock.getStock());

        // Act

        accountService.associate(account.getAccountId().toString(), dto);

        // Assert
        verify(accountRepository).findById(account.getAccountId());
        verify(stockRepository).findById(stock.getStock());
        verify(accountStockRepository).save(argThat(saved ->
                saved.getAccount().equals(account) &&
                        saved.getStock().equals(stock) &&
                        Objects.equals(saved.getQuantity(), accountStock.getQuantity()) &&
                        saved.getId().getAccountId().equals(account.getAccountId()) &&
                        saved.getId().getStockId().equals(stock.getStock())
        ));
    }


    @Test
    @DisplayName("Should Not Associate An Account With An Stock Successfully")
    void shouldNotAssociateAnAccountWithAStock() {
        UUID accountUUID = UUID.randomUUID();
        String stockId = "BBDC3";

        AccountStockDTO dto = new AccountStockDTO(stockId, 10);

        when(accountRepository.findById(accountUUID)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> accountService.associate(accountUUID.toString(), dto));
        verify(accountRepository).findById(accountUUID);
        verifyNoInteractions(stockRepository);
        verifyNoInteractions(accountStockRepository);
    }

    @Test
    void listStocks() {
    }
}