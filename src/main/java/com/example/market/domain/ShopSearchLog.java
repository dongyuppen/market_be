package com.example.market.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ShopSearchLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @Column(length=100) private String keyword;
    private LocalDateTime searchTime;

    @Column(length=64) private String userSessionId;

    @PrePersist void prePersist(){ if(searchTime==null) searchTime = LocalDateTime.now(); }
}