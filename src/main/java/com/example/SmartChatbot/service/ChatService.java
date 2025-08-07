package com.example.SmartChatbot.service;

import com.example.SmartChatbot.entity.Faq;
import com.example.SmartChatbot.repository.FaqRepository;
import com.example.SmartChatbot.util.StringSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private FaqRepository faqRepository;

    public String getAnswer(String question) {
        question = question.toLowerCase().trim();

        List<Faq> allFaqs = faqRepository.findAll();
        Faq bestMatch = null;
        double highestSimilarity = 0.0;

        for (Faq faq : allFaqs) {
            double sim = StringSimilarity.similarity(question, faq.getQuestion().toLowerCase());
            if (sim > highestSimilarity) {
                highestSimilarity = sim;
                bestMatch = faq;
            }
        }

        if (highestSimilarity > 0.1) {
            return bestMatch.getAnswer();
        } else {
            return "দুঃখিত, আমি আপনার প্রশ্নের সঠিক উত্তর দিতে পারছি না।";
        }
    }
}
