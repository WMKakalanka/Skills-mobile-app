package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;
import com.example.skillsexchangemobileapp.utils.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private Spinner roleSpinner;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signUpTextView;
    private ImageView passwordToggle;
    private DBHelper dbHelper;
    private boolean isPasswordVisible = false; // To track password visibility state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Make sure this matches your layout XML file name

        // Initialize views
        roleSpinner = findViewById(R.id.roleSpinner);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpTextView = findViewById(R.id.signUpTextView);
        passwordToggle = findViewById(R.id.passwordToggle); // Initialize password toggle ImageView
        // Example of an action
        Toast.makeText(this, "Welcome to Login Dashboard", Toast.LENGTH_SHORT).show();
        // Initialize the database helper
        dbHelper = new DBHelper(this);

        // Set up the Spinner (dropdown)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        // Set listener for role selection
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedRole = parentView.getItemAtPosition(position).toString();
                Toast.makeText(LoginActivity.this, "Selected Role: " + selectedRole, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Handle password visibility toggle
        passwordToggle.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Hide password
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggle.setImageResource(R.drawable.ic_visibility_off); // Use your "eye closed" icon
                isPasswordVisible = false;
            } else {
                // Show password
                passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggle.setImageResource(R.drawable.ic_visibility_on); // Use your "eye open" icon
                isPasswordVisible = true;
            }
            // Move the cursor to the end of the text
            passwordEditText.setSelection(passwordEditText.length());
        });

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String role = roleSpinner.getSelectedItem().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean userExists = dbHelper.checkUserCredentials(email, password, role);

            if (userExists) {
                Intent intent;
                if (role.equals("Learner")) {
                    intent = new Intent(LoginActivity.this, LearnerHomeActivity.class);
                } else if (role.equals("Resource People")) {
                    intent = new Intent(LoginActivity.this, ResourcePeopleHomeActivity.class);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid role", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle sign-up link click
        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}
