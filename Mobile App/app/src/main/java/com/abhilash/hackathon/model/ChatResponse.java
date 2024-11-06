package com.abhilash.hackathon.model;

import java.util.List;

public class ChatResponse {
    private String response;
    private List<String> context;

    public String getResponse() {
        return response;
    }

    public List<String> getContext() {
        return context;
    }

    public void setContext(List<String> context) {
        this.context = context;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
