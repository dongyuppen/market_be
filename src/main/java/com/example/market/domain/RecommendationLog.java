package com.example.market.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class RecommendationLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    @Column(length=50) private String reason;  // popular, keyword, category_popular 등
    private LocalDateTime recTime;

    @Column(length=64) private String userSessionId;

    @PrePersist void prePersist(){ if(recTime==null) recTime = LocalDateTime.now(); }
}
