package com.example.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.client.ChatClient;

@Configuration
public class AiConfig {

    private final OpenAiChatModel openAiChatModel;

    public AiConfig(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
    }

    @Bean
    public ChatClient chatClient() {
        return ChatClient.create(openAiChatModel);
    }
}