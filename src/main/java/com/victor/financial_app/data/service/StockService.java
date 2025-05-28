package com.victor.financial_app.data.service;

import com.victor.financial_app.data.repository.StockRepository;
import com.victor.financial_app.dtos.stock.CreateStockDTO;
import com.victor.financial_app.entity.Stock;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDTO stockDTO) {
        Stock stock = new Stock(stockDTO.stockId(), stockDTO.description());

        stockRepository.save(stock);
    }
}
