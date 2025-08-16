package com.example.market.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MarketDTO {
    private Long id;
    private String name;
    private String location;
    private String description;
}