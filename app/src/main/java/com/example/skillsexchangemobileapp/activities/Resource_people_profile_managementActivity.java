package com.example.skillsexchangemobileapp.activities;

import static androidx.core.app.ActivityCompat.startActivityForResult;

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

public class Resource_people_profile_managementActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;

    private EditText rtFirstName, rtLastName, rtPhone, rtEmail, rtSkill;
    private ImageView rtUpload;
    private Button rtbtnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resoruce_people_edit_profile);

        rtFirstName = findViewById(R.id.rt_first_name);
        rtLastName = findViewById(R.id.rt_last_name);
        rtPhone = findViewById(R.id.rt_phone);
        rtEmail = findViewById(R.id.rt_email);
        rtSkill = findViewById(R.id.rt_skill);
        rtUpload = findViewById(R.id.rt_upload);
        rtbtnUpdate = findViewById(R.id.rt_btn_update);

        rtUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to pick an image
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        rtbtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle update logic
                String firstName = rtFirstName.getText().toString();
                String lastName = rtLastName.getText().toString();
                String phone = rtPhone.getText().toString();
                String email = rtEmail.getText().toString();
                String skill = rtSkill.getText().toString();

                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    Toast.makeText(Resource_people_profile_managementActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Resource_people_profile_managementActivity.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            rtUpload.setImageURI(selectedImage);
        }
    }
}


