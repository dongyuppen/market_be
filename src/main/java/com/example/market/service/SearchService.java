package com.example.market.service;

import com.example.market.dto.PopularSearchDTO;
import com.example.market.entity.PopularSearch;
import com.example.market.repository.PopularSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final PopularSearchRepository popularSearchRepository;

    public List<PopularSearchDTO> getPopularKeywords() {
        return popularSearchRepository.findTop10ByOrderByCountDesc().stream()
                .map(p -> new PopularSearchDTO(p.getKeyword(), p.getCount()))
                .collect(Collectors.toList());
    }
}