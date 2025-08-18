package com.example.market.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 가게 이름 */
    private String name;

    /** 카테고리 (정육점/야채가게/생선가게/분식 등) */
    private String category;

    /** 가게 소개 */
    private String description;

    /** 시장 내 위치(예: 축산동 12호, 먹거리골목 3-2 등) */
    private String address;

    /** 영업시간 (예: "매일 09:00~20:00") */
    @Column(name = "open_hours")
    private String openHours;

    /** 소속 시장 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;
}
