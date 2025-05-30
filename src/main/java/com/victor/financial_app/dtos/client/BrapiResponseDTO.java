package com.victor.financial_app.dtos.client;

import com.victor.financial_app.data.client.StockDTO;

import java.util.List;

public record BrapiResponseDTO(List<StockDTO> results) {
}
