package com.abhilash.hackathon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.abhilash.hackathon.R;

public class MainActivity extends AppCompatActivity {

    private Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatButton = findViewById(R.id.chatButton);
        LinearLayout researcherButton = findViewById(R.id.researcherButton);
        LinearLayout officialButton = findViewById(R.id.officialButton);
        // Set click listener to open ChatActivity
        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

        researcherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent researcherIntent = new Intent(MainActivity.this, ResearcherActivity.class);
                startActivity(researcherIntent);
            }
        });

        officialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent officialIntent = new Intent(MainActivity.this, ResearcherActivity.class);
                startActivity(officialIntent);
            }
        });
    }
}