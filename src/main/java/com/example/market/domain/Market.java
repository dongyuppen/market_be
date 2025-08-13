package com.example.market.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Market {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marketId;

    @Column(nullable=false, length=100) private String name;
    @Column(length=255) private String location;
    @Column(columnDefinition = "TEXT") private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist void prePersist(){ createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate  void preUpdate(){ updatedAt = LocalDateTime.now(); }
}