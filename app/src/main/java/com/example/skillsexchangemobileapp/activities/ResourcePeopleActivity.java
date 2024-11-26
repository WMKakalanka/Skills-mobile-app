package com.example.skillsexchangemobileapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.skillsexchangemobileapp.R;

import java.util.Calendar;

public class ResourcePeopleActivity extends AppCompatActivity {

    private EditText startDateTimeEditText, endDateTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resource_people_activity_scheduling);

        // Initialize EditText fields
        startDateTimeEditText = findViewById(R.id.courseStartDateEditText);
        endDateTimeEditText = findViewById(R.id.courseEndDateEditText);

        // Set click listeners to show DateTime Picker
        startDateTimeEditText.setOnClickListener(v -> showDateTimePicker(startDateTimeEditText));
        endDateTimeEditText.setOnClickListener(v -> showDateTimePicker(endDateTimeEditText));
    }

    private void showDateTimePicker(EditText editText) {
        final Calendar calendar = Calendar.getInstance();

        // Show Date Picker Dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Set selected date in the calendar
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Show Time Picker Dialog after selecting the date
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                // Set selected time in the calendar
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                // Format and display the selected date and time
                String selectedDateTime = DateFormat.format("yyyy-MM-dd HH:mm", calendar).toString();
                editText.setText(selectedDateTime);

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);

            timePickerDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}
