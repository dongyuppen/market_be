package com.example.market.controller;

import com.example.market.dto.LogRequests;
import com.example.market.service.LogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    private String sessionIdFromHeader(String header){
        return (header!=null && !header.isBlank()) ? header : "anonymous";
    }

    @PostMapping("/search")
    public ResponseEntity<?> logSearch(@RequestHeader(value="X-Session-Id", required=false) String session,
                                       @Valid @RequestBody LogRequests.Search req){
        logService.logSearch(req.shopId(), req.keyword(), sessionIdFromHeader(session));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/recommend")
    public ResponseEntity<?> logRecommend(@RequestHeader(value="X-Session-Id", required=false) String session,
                                          @Valid @RequestBody LogRequests.Recommend req){
        logService.logRecommend(req.shopId(), req.reason(), sessionIdFromHeader(session));
        return ResponseEntity.ok().build();
    }
}