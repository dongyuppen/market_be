package com.example.market.dto;

import com.example.market.entity.Market;

import java.util.List;

public record MarketDetailResponse(
        Long id,
        String name,
        String location,
        String description,
        List<StoreResponse> stores
) {
    public static MarketDetailResponse from(Market market) {
        return new MarketDetailResponse(
                market.getId(),
                market.getName(),
                market.getLocation(),
                market.getDescription(),
                market.getStores() == null ? List.of() :
                        market.getStores().stream()
                                .map(StoreResponse::from)
                                .toList()
        );
    }
}