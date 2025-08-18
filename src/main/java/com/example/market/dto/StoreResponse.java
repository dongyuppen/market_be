package com.example.market.dto;

import com.example.market.entity.Store;

public record StoreResponse(Long id, String name, String category, String description) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getCategory(),
                store.getDescription()
        );
    }
}