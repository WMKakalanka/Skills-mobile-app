package com.example.skillsexchangemobileapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;
import com.example.skillsexchangemobileapp.utils.DBHelper;

import java.util.Calendar;

public class ResourcePeopleActivityScheduling extends AppCompatActivity {

    private EditText courseTitleEditText, courseDescriptionEditText, courseStartDateEditText, courseEndDateEditText;
    private Button addCourseButton;

    private DBHelper dbHelper; // Use the proper database helper class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_people_activity_scheduling);

        // Initialize views
        courseTitleEditText = findViewById(R.id.courseTitleEditText);
        courseDescriptionEditText = findViewById(R.id.courseDescriptionEditText);
        courseStartDateEditText = findViewById(R.id.courseStartDateEditText);
        courseEndDateEditText = findViewById(R.id.courseEndDateEditText);
        addCourseButton = findViewById(R.id.addCourseButton);

        // Initialize database helper
        dbHelper = new DBHelper(this);

        // Set listeners for date and time pickers
        courseStartDateEditText.setOnClickListener(v -> showDateTimePicker(courseStartDateEditText));
        courseEndDateEditText.setOnClickListener(v -> showDateTimePicker(courseEndDateEditText));

        // Set listener for the Add Course button
        addCourseButton.setOnClickListener(v -> saveCourseDetails());
    }

    private void showDateTimePicker(EditText targetEditText) {
        Calendar calendar = Calendar.getInstance();

        // Open DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    // Set selected date
                    String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;

                    // Open TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                            (timeView, hourOfDay, minute) -> {
                                // Set selected time
                                String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                                targetEditText.setText(selectedDate + " " + selectedTime);
                            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void saveCourseDetails() {
        // Get data from input fields
        String courseTitle = courseTitleEditText.getText().toString().trim();
        String courseDescription = courseDescriptionEditText.getText().toString().trim();
        String courseStartDate = courseStartDateEditText.getText().toString().trim();
        String courseEndDate = courseEndDateEditText.getText().toString().trim();

        // Validate input
        if (courseTitle.isEmpty() || courseDescription.isEmpty() || courseStartDate.isEmpty() || courseEndDate.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save course details to the database
        boolean isInserted = dbHelper.insertCourseDetails(courseTitle, courseDescription, courseStartDate, courseEndDate);

        if (isInserted) {
            Toast.makeText(this, "Course added successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Failed to add course!", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        courseTitleEditText.setText("");
        courseDescriptionEditText.setText("");
        courseStartDateEditText.setText("");
        courseEndDateEditText.setText("");
    }
}
