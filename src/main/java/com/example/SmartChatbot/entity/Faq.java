package com.example.SmartChatbot.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;
}
