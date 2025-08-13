package com.example.market.repository;

import com.example.market.domain.ShopSearchLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShopSearchLogRepository extends JpaRepository<ShopSearchLog, Long> {
    @Query("SELECT l.shop.shopId, COUNT(l) FROM ShopSearchLog l WHERE l.searchTime >= :from GROUP BY l.shop.shopId")
    List<Object[]> countSince(LocalDateTime from);
}