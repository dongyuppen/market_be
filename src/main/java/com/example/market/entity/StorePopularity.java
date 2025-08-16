package com.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_popularity")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StorePopularity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", unique = true)
    private Store store;

    private int count; // 검색/조회 누적 횟수
}