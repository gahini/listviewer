package com.example.employeelistapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.*;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {

    private final List<User> userList;

    public UserAdapter(Context context, List<User> userList) {
        super(context, 0, userList);
        this.userList = userList;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.user_item, parent, false);
        TextView tvId = view.findViewById(R.id.tvId);
        TextView tvFName = view.findViewById(R.id.tvFirstName);
        TextView tvLName = view.findViewById(R.id.tvLastName);
        TextView tvEmail = view.findViewById(R.id.tvEmail);

        User user = userList.get(position);

        tvId.setText("ID: " + user.getId());
        tvFName.setText("First Name: " + user.getFirst_name());
        tvLName.setText("Last Name: " + user.getLast_name());
        tvEmail.setText("Email: " + user.getEmail());

        return view;
    }
}
