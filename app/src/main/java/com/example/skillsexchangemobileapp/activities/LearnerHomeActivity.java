package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

public class LearnerHomeActivity extends AppCompatActivity {

    private LinearLayout coursesCard, schedulingCard;
    private Button profileManagementButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learner_home); // Make sure the XML filename matches

        // Initialize UI components
        coursesCard = findViewById(R.id.CourseCard); // Corrected ID
        schedulingCard = findViewById(R.id.viewCourseCard);
        profileManagementButton = findViewById(R.id.profileManagementButton);

        // Set click listener for "Courses"
        coursesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LearnerHomeActivity.this, CourseActivity.class);
                startActivity(intent);
                Toast.makeText(LearnerHomeActivity.this, "Navigating to Courses", Toast.LENGTH_SHORT).show();
            }
        });

        // Set click listener for "Scheduling Calendar"
        schedulingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SchedulingActivity (replace with your activity)
                Intent intent = new Intent(LearnerHomeActivity.this, SchedulingActivity.class);
                startActivity(intent);
                Toast.makeText(LearnerHomeActivity.this, "Navigating to Scheduling Calendar", Toast.LENGTH_SHORT).show();
            }
        });
        // Example of an action
        Toast.makeText(this, "Welcome to Learner Dashboard", Toast.LENGTH_SHORT).show();
        // Set click listener for "Profile Management"
        profileManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileManagementActivity (replace with your activity)
                Intent intent = new Intent(LearnerHomeActivity.this, Learner_profile_managementActivity.class);
                startActivity(intent);
                Toast.makeText(LearnerHomeActivity.this, "Navigating to Profile Management", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
