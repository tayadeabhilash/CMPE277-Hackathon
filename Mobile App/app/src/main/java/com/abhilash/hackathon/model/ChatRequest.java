package com.abhilash.hackathon.model;

public class ChatRequest {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public ChatRequest(String message) {
        this.message = message;
    }
}
