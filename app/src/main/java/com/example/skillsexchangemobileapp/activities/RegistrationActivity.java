package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;
import com.example.skillsexchangemobileapp.utils.DBHelper;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText,
            interestsEditText, skillsEditText, experienceEditText;
    private Spinner roleSpinner;
    private Button registerButton;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        interestsEditText = findViewById(R.id.interestsEditText);
        skillsEditText = findViewById(R.id.skillsEditText);
        experienceEditText = findViewById(R.id.experienceEditText);
        roleSpinner = findViewById(R.id.roleSpinner);
        registerButton = findViewById(R.id.registerButton);

        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Populate the Spinner with roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        // Example of an action
        Toast.makeText(this, "Welcome to Registration Dashboard", Toast.LENGTH_SHORT).show();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from the UI
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                // Example of an action

                // Validate the input
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if email contains '@' sign
                if (!email.contains("@")) {
                    Toast.makeText(RegistrationActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password has at least 6 characters
                if (password.length() < 6) {
                    Toast.makeText(RegistrationActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get role from Spinner
                String role = roleSpinner.getSelectedItem().toString();

                // Get the specific fields based on role
                String interests = interestsEditText.getText().toString().trim();
                String skills = skillsEditText.getText().toString().trim();
                int experience = 0;

                if (role.equals("Resource People")) {
                    // For Resource People, get skills and experience
                    skills = skillsEditText.getText().toString().trim();
                    try {
                        experience = Integer.parseInt(experienceEditText.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        experience = 0; // Default if not a valid number
                    }
                }

                // Insert user data into the database
                long result = dbHelper.insertUser(name, email, password, role, interests, skills, experience);

                if (result != -1) {
                    Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Redirect to LoginActivity
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Optional: close the current activity
                } else {
                    Toast.makeText(RegistrationActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}

