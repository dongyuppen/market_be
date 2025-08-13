package com.example.market.service;

import com.example.market.domain.*;
import com.example.market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {
    private final ShopRepository shopRepository;

    public List<Shop> search(Long marketId, String category, String keyword){
        return shopRepository.search(marketId, category, keyword);
    }

    public Shop get(Long shopId){
        return shopRepository.findById(shopId).orElseThrow(() -> new NoSuchElementException("Shop not found"));
    }
}