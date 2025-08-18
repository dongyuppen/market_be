package com.example.market.controller;

import com.example.market.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatbotController {

    private final ChatbotService chatbotService;

    @GetMapping
    public String ask(@RequestParam String q) {
        return chatbotService.askChatbot(q);
    }
}