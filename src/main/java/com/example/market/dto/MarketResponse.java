package com.example.market.dto;

import com.example.market.entity.Market;

public record MarketResponse(Long id, String name, String location, String description) {
    public static MarketResponse from(Market market) {
        return new MarketResponse(
                market.getId(),
                market.getName(),
                market.getLocation(),
                market.getDescription()
        );
    }
}