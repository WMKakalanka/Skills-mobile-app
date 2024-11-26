package com.example.skillsexchangemobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.activities.GetStartedActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button by ID
        Button btnGetStarted = findViewById(R.id.btnGetStarted);

        // Set a click listener on the button
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to GetStartedActivity
                Intent intent = new Intent(MainActivity.this, GetStartedActivity.class);
                startActivity(intent);
            }
        });
    }
}
