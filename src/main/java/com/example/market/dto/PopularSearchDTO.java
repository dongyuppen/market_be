package com.example.market.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PopularSearchDTO {
    private String keyword;
    private int count;
}