package com.example.employeelistapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText searchBox;
    private Button btnAddUser;
    private Button btnSubmit;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private List<User> filteredList = new ArrayList<>();
    private static final int REQUEST_CODE = 101;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        listView = findViewById(R.id.listView);
        searchBox = findViewById(R.id.searchBox);
        btnSubmit = findViewById(R.id.btnSubmit);  // Initialize submit button
        btnAddUser = findViewById(R.id.btnAddUser);  // Initialize Add User button

        // Ensure btnSubmit is not null
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> {
                // Handle button click logic
            });
        } else {
            Log.e("MainActivity", "Button with ID btnSubmit is null");
        }

        // Set up adapter and list view
        adapter = new UserAdapter(this, filteredList);
        listView.setAdapter(adapter);

        fetchUsers();

        // Add user button click listener
        btnAddUser.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        // Search box text watcher
        searchBox.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void afterTextChanged(Editable s) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
        });
    }

    private void fetchUsers() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api = retrofit.create(ApiService.class);
        api.getUsers(2).enqueue(new Callback<UserResponse>() {
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body().getData();
                    filteredList.clear();
                    filteredList.addAll(userList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text) {
        filteredList.clear();
        for (User user : userList) {
            if (user.getFirst_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String fname = data.getStringExtra("first_name");
            String lname = data.getStringExtra("last_name");
            int id = userList.size() + 1;
            User newUser = new User(id, fname, lname, fname.toLowerCase() + "." + lname.toLowerCase() + "@reqres.in");
            userList.add(newUser);
            filteredList.add(newUser);
            adapter.notifyDataSetChanged();
        }
    }
}
