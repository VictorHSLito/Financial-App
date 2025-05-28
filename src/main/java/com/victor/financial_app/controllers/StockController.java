package com.victor.financial_app.controllers;

import com.victor.financial_app.data.service.StockService;
import com.victor.financial_app.dtos.stock.CreateStockDTO;
import com.victor.financial_app.entity.Stock;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/stocks")
    public ResponseEntity<Stock> createStock(@RequestBody CreateStockDTO createStockDTO) {
        stockService.createStock(createStockDTO);
        return ResponseEntity.ok().build();
    }

}
