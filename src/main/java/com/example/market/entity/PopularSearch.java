package com.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "popular_searches")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PopularSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;
    private int count;
}