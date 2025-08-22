package com.example.market.controller;

import com.example.market.ai.MarketAssistant;   // ① LangChain4j AI Service
import com.example.market.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    // 기존 Spring AI 기반 서비스
    private final ChatbotService chatbotService;

    // ② LangChain4j @AiService 구현체 주입
    private final MarketAssistant marketAssistant;

    // 기존 엔드포인트 (Spring AI ChatClient 사용)
    @GetMapping
    public String ask(@RequestParam String q) {
        return chatbotService.askChatbot(q);
    }

    // ③ LangChain4j 호출용 엔드포인트
    @PostMapping("/ask-lc4j")
    public String askLangChain(@RequestBody Map<String, String> body) {
        String q = body.getOrDefault("question", "");
        return marketAssistant.answer(q);
    }
}
