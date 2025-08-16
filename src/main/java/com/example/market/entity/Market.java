package com.example.market.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "markets")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Market {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // 시장 이름
    private String location;    // 주소/위치
    private String description; // 소개

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Store> stores;
}