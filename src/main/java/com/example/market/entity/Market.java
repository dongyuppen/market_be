package com.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "markets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String description;
}