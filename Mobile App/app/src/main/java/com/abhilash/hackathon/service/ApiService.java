package com.abhilash.hackathon.service;

import com.abhilash.hackathon.model.ChatRequest;
import com.abhilash.hackathon.model.ChatResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/chat")
    Call<ChatResponse> sendMessage(@Body ChatRequest request);
}
