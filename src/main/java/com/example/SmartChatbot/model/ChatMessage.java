package com.example.SmartChatbot.model;

public class ChatMessage {
    private String sender; // "user" or "bot"
    private String message;

    public ChatMessage() {}

    public ChatMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    // Getters and Setters
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}