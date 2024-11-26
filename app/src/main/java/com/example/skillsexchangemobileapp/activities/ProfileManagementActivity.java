package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;
import com.example.skillsexchangemobileapp.utils.DBHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ProfileManagementActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profilePicture;
    private EditText editName, editSkills;
    private Button btnSave;

    private Bitmap selectedBitmap;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize views
        profilePicture = findViewById(R.id.profilePicture);
        editName = findViewById(R.id.nameEditText);
        editSkills = findViewById(R.id.skillsEditText);
        btnSave = findViewById(R.id.saveButton);

        // Initialize database helper
        dbHelper = new DBHelper(this);

        // Load existing user profile
        loadUserProfile();

        // Set profile picture click listener
        profilePicture.setOnClickListener(v -> openImagePicker());

        // Set save button click listener
        btnSave.setOnClickListener(v -> saveUserProfile());
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                selectedBitmap = resizeBitmap(selectedBitmap, 500, 500); // Resize for uniformity
                profilePicture.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float bitmapRatio = (float) width / height;

        if (bitmapRatio > 1) {
            width = maxWidth;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxHeight;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String saveImageToInternalStorage(Bitmap bitmap) {
        try {
            String fileName = "profile_picture.png";
            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return fileName; // Return file path
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap loadImageFromInternalStorage(String fileName) {
        try {
            FileInputStream fis = openFileInput(fileName);
            return BitmapFactory.decodeStream(fis);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void loadUserProfile() {
        String[] profile = dbHelper.getUserProfile();
        if (profile != null) {
            editName.setText(profile[0]);
            editSkills.setText(profile[1]);

            // Load profile picture if path exists
            String profileImagePath = profile[2];
            if (profileImagePath != null && !profileImagePath.isEmpty()) {
                Bitmap profileImage = loadImageFromInternalStorage(profileImagePath);
                if (profileImage != null) {
                    profilePicture.setImageBitmap(profileImage);
                }
            }
        }
    }

    private void saveUserProfile() {
        String name = editName.getText().toString().trim();
        String skills = editSkills.getText().toString().trim();
        String picturePath = null;

        if (name.isEmpty() || skills.isEmpty()) {
            Toast.makeText(this, "Name and skills are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedBitmap != null) {
            picturePath = saveImageToInternalStorage(selectedBitmap);
        }

        boolean isUpdated = dbHelper.updateUserProfile(name, skills, picturePath);
        if (isUpdated) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to update profile!", Toast.LENGTH_SHORT).show();
        }
    }
}
