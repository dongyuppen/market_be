package com.example.market.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StoreCreateRequest(
        @NotNull Long marketId,
        @NotBlank String name,
        String category,
        String description
) {}