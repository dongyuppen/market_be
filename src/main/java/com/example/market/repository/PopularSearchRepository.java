package com.example.market.repository;

import com.example.market.entity.PopularSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopularSearchRepository extends JpaRepository<PopularSearch, Long> {
    List<PopularSearch> findTop10ByOrderByCountDesc();
}