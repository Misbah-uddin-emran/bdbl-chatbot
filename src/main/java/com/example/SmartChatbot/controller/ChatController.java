//package com.example.SmartChatbot.controller;
//
//import com.example.SmartChatbot.model.ChatMessage;
//import com.example.SmartChatbot.model.ChatRequest;
//
//import com.example.SmartChatbot.repository.FaqRepository;
//import com.example.SmartChatbot.service.ChatService;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class ChatController {
//
//    @Autowired
//    private ChatService chatService;
//@Autowired
// private FaqRepository faqRepository;
//
//
//    @GetMapping("/")
//    public String showChatPage(Model model) {
//        model.addAttribute("chatRequest", new ChatRequest());
//        model.addAttribute("faqs", faqRepository.findTop10ByOrderByIdDesc());
//
//        // নতুন: খালি চ্যাট হিস্টোরি দিলে শুরুতে কিছু না দেখায়
//        model.addAttribute("chatHistory", new ArrayList<ChatMessage>());
//        return "chat";
//    }
//
//    @PostMapping("/ask")
//    public String askQuestion(@ModelAttribute ChatRequest chatRequest, Model model,
//                              @SessionAttribute(value = "chatHistory", required = false) List<ChatMessage> chatHistory,
//                              HttpSession session) {
//
//        if (chatHistory == null) {
//            chatHistory = new ArrayList<>();
//        }
//
//        String userQuestion = chatRequest.getQuestion();
//        String answer = chatService.getAnswer(userQuestion); // তোমার সার্ভিস লজিক
//
//        chatHistory.add(new ChatMessage("user", userQuestion));
//        chatHistory.add(new ChatMessage("bot", answer));
//
//        session.setAttribute("chatHistory", chatHistory);
//
//        model.addAttribute("chatRequest", new ChatRequest());
//        model.addAttribute("chatHistory", chatHistory);
//        model.addAttribute("faqs", faqRepository.findTop10ByOrderByIdDesc());
//
//        return "chat";
//    }
//
//
//    @PostMapping("/clear")
//    public String clearChatHistory(HttpSession session) {
//        session.removeAttribute("chatHistory");  // সেশন থেকে সব মেসেজ মুছে ফেলবে
//        return "redirect:/";  // হোম পেজে রিডাইরেক্ট করবে
//    }
//
//
//}


package com.example.SmartChatbot.controller;

import com.example.SmartChatbot.model.ChatMessage;
import com.example.SmartChatbot.model.ChatRequest;
import com.example.SmartChatbot.entity.Faq;
import com.example.SmartChatbot.repository.FaqRepository;
import com.example.SmartChatbot.service.ChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private FaqRepository faqRepository;

    @GetMapping("/")
    public String showChatPage(Model model, HttpSession session) {
        model.addAttribute("chatRequest", new ChatRequest());
        model.addAttribute("faqs", faqRepository.findTop10ByOrderByIdDesc());

        List<ChatMessage> chatHistory = (List<ChatMessage>) session.getAttribute("chatHistory");
        if (chatHistory == null) {
            chatHistory = new ArrayList<>();
        }
        model.addAttribute("chatHistory", chatHistory);

        return "chat";
    }

    @PostMapping("/ask")
    public String askQuestion(@ModelAttribute ChatRequest chatRequest, Model model,
                              HttpSession session) {

        List<ChatMessage> chatHistory = (List<ChatMessage>) session.getAttribute("chatHistory");
        if (chatHistory == null) {
            chatHistory = new ArrayList<>();
        }

        String userQuestion = chatRequest.getQuestion();
        String answer = chatService.getAnswer(userQuestion);

        chatHistory.add(new ChatMessage("user", userQuestion));
        chatHistory.add(new ChatMessage("bot", answer));

        session.setAttribute("chatHistory", chatHistory);

        model.addAttribute("chatRequest", new ChatRequest());
        model.addAttribute("chatHistory", chatHistory);
        model.addAttribute("faqs", faqRepository.findTop10ByOrderByIdDesc());

        return "chat";
    }

    @PostMapping("/clear")
    public String clearChatHistory(HttpSession session) {
        session.removeAttribute("chatHistory");
        return "redirect:/";
    }

    // AJAX API: র‍্যান্ডম ৫টি FAQ JSON আকারে রিটার্ন করবে
    @GetMapping("/api/faqs/random")
    @ResponseBody
    public List<Faq> getRandomFaqs() {
        return faqRepository.findRandomFaqs(5);
    }
}

