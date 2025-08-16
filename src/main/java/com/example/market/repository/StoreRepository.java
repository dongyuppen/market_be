package com.example.market.repository;

import com.example.market.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByMarket_Id(Long marketId);

    @Query("""
        select s from Store s
        where lower(s.name) like lower(concat('%', :keyword, '%'))
           or lower(s.category) like lower(concat('%', :keyword, '%'))
           or lower(s.description) like lower(concat('%', :keyword, '%'))
    """)
    List<Store> search(String keyword);
}