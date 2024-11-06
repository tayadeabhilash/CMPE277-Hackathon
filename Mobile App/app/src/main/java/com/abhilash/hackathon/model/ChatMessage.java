package com.abhilash.hackathon.model;

import java.util.List;

public class ChatMessage {
    private String message;
    private boolean isUser;
    private List<String> context;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public List<String> getContext() {
        return context;
    }

    public void setContext(List<String> context) {
        this.context = context;
    }

    public ChatMessage(String message, boolean isUser) {
        this(message, isUser, null);
    }

    public ChatMessage(String message, boolean isUser, List<String> context) {
        this.message = message;
        this.isUser = isUser;
        this.context = context;
    }

    // Getters and setters
}