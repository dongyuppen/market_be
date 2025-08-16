package com.example.market.dto;

import jakarta.validation.constraints.NotNull;

public record RecommendationCreateRequest(
        @NotNull Long shopId,
        String reason
) {}