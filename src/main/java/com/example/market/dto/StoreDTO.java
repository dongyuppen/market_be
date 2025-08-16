package com.example.market.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StoreDTO {
    private Long id;
    private String name;
    private String category;
    private String description;
    private Long marketId;

}