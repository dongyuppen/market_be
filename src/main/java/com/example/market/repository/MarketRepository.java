package com.example.market.repository;

import com.example.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    @Query("select m from Market m left join fetch m.stores where m.id = :id")
    Optional<Market> findByIdWithStores(Long id);
}