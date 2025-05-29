package com.victor.financial_app.controllers;

import com.victor.financial_app.data.repository.AccountStockRepository;
import com.victor.financial_app.data.service.AccountService;
import com.victor.financial_app.dtos.account.AccountStockDTO;
import com.victor.financial_app.dtos.account.AccountStockResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService, AccountStockRepository accountStockRepository) {
        this.accountService = accountService;
    }


    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> getAllAccounsAndStockAssociated(@PathVariable("accountId") String accountId) {
        var accountStockResponse = accountService.listStocks(accountId);
        return ResponseEntity.ok(accountStockResponse);
    }


    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Void> associateAccountToStock(@PathVariable("accountId") String accountId,
                                                        @RequestBody AccountStockDTO accountStockDTO) {
        accountService.associate(accountId, accountStockDTO);

        return ResponseEntity.ok().build();
    }
}
