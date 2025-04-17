package com.example.employeelistapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

public class AddUserActivity extends Activity {

    private EditText etFirstName, etLastName, etEmail;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(view -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            Intent intent = new Intent();
            intent.putExtra("first_name", firstName);
            intent.putExtra("last_name", lastName);
            intent.putExtra("email", email);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
