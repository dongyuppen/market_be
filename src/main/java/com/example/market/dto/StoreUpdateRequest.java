package com.example.market.dto;

public record StoreUpdateRequest(
        String name,
        String category,
        String description
) {}