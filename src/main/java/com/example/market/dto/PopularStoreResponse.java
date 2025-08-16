package com.example.market.dto;

public record PopularStoreResponse(
        Long id, String name, String category, int searchCount
) {}