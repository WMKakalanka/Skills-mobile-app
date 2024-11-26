package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseActivity extends AppCompatActivity {

    private SearchView searchView;
    private ArrayList<String> categories;
    private HashMap<String, LinearLayout> categoryCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        // Initialize SearchView
        searchView = findViewById(R.id.searchView);

        // Initialize categories and their corresponding views
        categories = new ArrayList<>();
        categories.add("Music");
        categories.add("Photography");
        categories.add("Fitness");
        categories.add("Dancing");
        categories.add("Cooking");
        categories.add("Programming");
        categories.add("Speaking");
        categories.add("Singing");

        // Map category names to LinearLayouts
        categoryCards = new HashMap<>();
        categoryCards.put("Music", findViewById(R.id.musicCard));
        categoryCards.put("Photography", findViewById(R.id.photographyCard));
        categoryCards.put("Fitness", findViewById(R.id.fitnessCard));
        categoryCards.put("Dancing", findViewById(R.id.dancingCard));
        categoryCards.put("Cooking", findViewById(R.id.cookingCard));
        categoryCards.put("Programming", findViewById(R.id.programmingCard));
        categoryCards.put("Speaking", findViewById(R.id.speakingCard));
        categoryCards.put("Singing", findViewById(R.id.singingCard));

        // Set up listeners for the category cards
        for (String category : categories) {
            setupCategoryCard(categoryCards.get(category), category);
        }

        // Implement SearchView listener for dynamic filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Optional: Handle search submit action
                Toast.makeText(CourseActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
                return false; // Let SearchView handle default actions
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCategories(newText);
                return true; // Indicate that the event is handled
            }
        });
    }

    // Method to set up click listeners for category cards
    private void setupCategoryCard(LinearLayout card, String category) {
        if (card != null) {
            card.setOnClickListener(v -> showCategory(category));
        }
    }

    // Method to show the selected category
    private void showCategory(String category) {
        Toast.makeText(CourseActivity.this, "Selected Category: " + category, Toast.LENGTH_SHORT).show();

        // Check if the selected category is "Programming" and navigate to the respective activity
        if ("Programming".equals(category)) {
            Intent intent = new Intent(CourseActivity.this, ViewAllCoursesActivity.class);
            startActivity(intent);
        }
        // You can add similar conditions for other categories if needed
    }

    // Method to filter categories based on search input
    private void filterCategories(String query) {
        for (String category : categories) {
            LinearLayout card = categoryCards.get(category);
            if (card != null) {
                if (category.toLowerCase().contains(query.toLowerCase())) {
                    card.setVisibility(View.VISIBLE);
                } else {
                    card.setVisibility(View.GONE);
                }
            }
        }
    }
}
