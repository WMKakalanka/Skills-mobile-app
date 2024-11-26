package com.example.skillsexchangemobileapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

public class EditUserProfile extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private EditText etFirstName, etLastName, etPhone, etEmail, etSkill;
    private ImageView ivUpload;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etEmail = findViewById(R.id.et_email);
        etSkill = findViewById(R.id.et_skill);
        ivUpload = findViewById(R.id.iv_upload);
        btnUpdate = findViewById(R.id.btn_update);

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update logic
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String skill = etSkill.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(EditUserProfile.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditUserProfile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ivUpload.setImageURI(selectedImage);
        }
    }
}
