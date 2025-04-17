package com.example.employeelistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private EditText edtFirstName, edtLastName;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);  // Use the correct layout name

        // Initialize the views
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Set up the button click listener
        btnSubmit.setOnClickListener(v -> {
            // Get the input values from the EditTexts
            String fname = edtFirstName.getText().toString().trim();
            String lname = edtLastName.getText().toString().trim();

            // Create an Intent to send data back to the calling activity
            Intent result = new Intent();
            result.putExtra("first_name", fname);
            result.putExtra("last_name", lname);

            // Set the result code and finish the activity
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }
}
