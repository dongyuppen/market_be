package com.example.market.repository;

import com.example.market.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query("""
        SELECT s FROM Shop s
        WHERE s.market.marketId = :marketId
          AND (:category IS NULL OR s.category = :category)
          AND (:keyword IS NULL OR (LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))))
        """)
    List<Shop> search(Long marketId, String category, String keyword);
}