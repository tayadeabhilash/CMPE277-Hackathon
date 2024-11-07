package com.abhilash.hackathon.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhilash.hackathon.R;
import com.abhilash.hackathon.client.ChatAdapter;
import com.abhilash.hackathon.client.RetrofitClient;
import com.abhilash.hackathon.model.ChatMessage;
import com.abhilash.hackathon.model.ChatRequest;
import com.abhilash.hackathon.model.ChatResponse;
import com.abhilash.hackathon.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ProgressBar progressBar;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Enable Action Bar's "Up" button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Show back button
            getSupportActionBar().setTitle("Chat");  // Optional: Set title for chat screen
        }

        initializeViews();
        setupRecyclerView();
        setupClickListeners();
    }

    // Handle action bar's back button click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();  // Close current activity and go back to previous one
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeViews() {
        chatRecyclerView = findViewById(R.id.recyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        progressBar = findViewById(R.id.progressBar);
        messages = new ArrayList<>();
        apiService = RetrofitClient.getInstance().create(ApiService.class);
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(messages);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void setupClickListeners() {
        sendButton.setOnClickListener(v -> {
            String message = messageInput.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
            }
        });
    }

    private void sendMessage(String messageText) {
        // Add user message to chat
        ChatMessage userMessage = new ChatMessage(messageText, true);
        messages.add(userMessage);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        chatRecyclerView.scrollToPosition(messages.size() - 1);

        // Clear input and show progress
        messageInput.setText("");
        progressBar.setVisibility(View.VISIBLE);

        // Make API call
        ChatRequest request = new ChatRequest(messageText);
        apiService.sendMessage(request).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ChatMessage botMessage = new ChatMessage(
                            response.body().getResponse(),
                            false,
                            response.body().getContext()
                    );
                    messages.add(botMessage);
                    chatAdapter.notifyItemInserted(messages.size() - 1);
                    chatRecyclerView.scrollToPosition(messages.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                ChatMessage errorMessage = new ChatMessage(
                        "Sorry, I couldn't connect to the server.",
                        false
                );
                messages.add(errorMessage);
                chatAdapter.notifyItemInserted(messages.size() - 1);
            }
        });
    }
}