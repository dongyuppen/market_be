package com.example.market.controller;

import com.example.market.dto.PopularSearchDTO;
import com.example.market.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/popular")
    public List<PopularSearchDTO> getPopularKeywords() {
        return searchService.getPopularKeywords();
    }
}