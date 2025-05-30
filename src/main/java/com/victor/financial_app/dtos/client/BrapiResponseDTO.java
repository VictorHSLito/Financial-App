package com.victor.financial_app.dtos.client;

import java.util.List;

public record BrapiResponseDTO(List<StockDTO> results) {
}
