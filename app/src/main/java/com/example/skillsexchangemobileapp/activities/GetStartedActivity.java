package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

public class GetStartedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(GetStartedActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(GetStartedActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}
