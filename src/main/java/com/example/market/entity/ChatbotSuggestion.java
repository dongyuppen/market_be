package com.example.market.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chatbot_suggestion")
@Getter @Setter
public class ChatbotSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String text;

    @Column(length = 50)
    private String category;

    @Column(nullable = false)
    private boolean pinned = true;

    @Column(nullable = false)
    private int clicks = 0;
}
