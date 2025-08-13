package com.example.market.repository;

import com.example.market.domain.RecommendationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RecommendationLogRepository extends JpaRepository<RecommendationLog, Long> {
    @Query("SELECT l.shop.shopId, COUNT(l) FROM RecommendationLog l WHERE l.recTime >= :from GROUP BY l.shop.shopId")
    List<Object[]> countSince(LocalDateTime from);
}