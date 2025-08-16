package com.example.market.repository;

import com.example.market.entity.Store;
import com.example.market.entity.StorePopularity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorePopularityRepository extends JpaRepository<StorePopularity, Long> {
    Optional<StorePopularity> findByStore(Store store);
    List<StorePopularity> findTop10ByOrderByCountDesc();
}