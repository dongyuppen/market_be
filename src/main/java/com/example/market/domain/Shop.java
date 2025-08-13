package com.example.market.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Shop {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="market_id", nullable=false)
    private Market market;

    @Column(nullable=false, length=120) private String name;
    @Column(length=50) private String category;  // 음식, 의류 등
    @Column(columnDefinition="TEXT") private String description;
    @Column(length=120) private String location; // 시장 내 위치(호수)
    @Column(length=30) private String phone;
    @Column(length=255) private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist void prePersist(){ createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate  void preUpdate(){ updatedAt = LocalDateTime.now(); }
}