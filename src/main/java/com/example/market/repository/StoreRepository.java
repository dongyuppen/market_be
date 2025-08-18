package com.example.market.repository;

import com.example.market.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // 시장 전체 가게
    List<Store> findByMarket_Id(Long marketId);

    // [추가] 시장 + 카테고리(대/소문자 무시) 필터
    List<Store> findByMarket_IdAndCategoryContainingIgnoreCase(Long marketId, String category);

    // 키워드(가게명/카테고리/설명) 검색
    @Query("""
        select s from Store s
        where lower(s.name) like lower(concat('%', :keyword, '%'))
           or lower(s.category) like lower(concat('%', :keyword, '%'))
           or lower(s.description) like lower(concat('%', :keyword, '%'))
    """)
    List<Store> search(@Param("keyword") String keyword);

    // [추가] 시장 한정 키워드 검색
    @Query("""
        select s from Store s
        where s.market.id = :marketId
          and (
                 lower(s.name) like lower(concat('%', :keyword, '%'))
              or lower(s.category) like lower(concat('%', :keyword, '%'))
              or lower(s.description) like lower(concat('%', :keyword, '%'))
          )
    """)
    List<Store> searchInMarket(@Param("marketId") Long marketId, @Param("keyword") String keyword);
}
