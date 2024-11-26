package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

public class ResourcePeopleHomeActivity extends AppCompatActivity {

    private LinearLayout resourceManagementCard;
    private LinearLayout viewCourseCard;
    private Button Re_profileManagementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_people_home);

        resourceManagementCard = findViewById(R.id.resourceManagementCard);
        viewCourseCard = findViewById(R.id.viewCourseCard);
        Re_profileManagementButton = findViewById(R.id.Re_profileManagementButton);

        // Handle click on "View Courses" card
        viewCourseCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcePeopleHomeActivity.this, ViewAllCoursesActivity.class);
                startActivity(intent);
                Toast.makeText(ResourcePeopleHomeActivity.this, "Navigating to View All Courses", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle click on "Resource Management" card
        resourceManagementCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcePeopleHomeActivity.this, ResourcePeopleActivity.class);
                startActivity(intent);
                Toast.makeText(ResourcePeopleHomeActivity.this, "Navigating to Resource People Activity Scheduling", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle click on "Profile Management" button
        Re_profileManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResourcePeopleHomeActivity.this, Resource_people_profile_managementActivity.class);
                startActivity(intent);
                Toast.makeText(ResourcePeopleHomeActivity.this, "Navigating to Profile Management", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
