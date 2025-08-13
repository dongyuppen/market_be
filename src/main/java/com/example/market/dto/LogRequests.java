package com.example.market.dto;

import jakarta.validation.constraints.*;

public class LogRequests {
    public record Search(
            @NotNull Long shopId,
            String keyword
    ){}
    public record Recommend(
            @NotNull Long shopId,
            @NotBlank String reason
    ){}
}