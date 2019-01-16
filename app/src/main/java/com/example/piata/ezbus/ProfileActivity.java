package com.example.piata.ezbus;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView email;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.Email);
        username = findViewById(R.id.Username);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {
        email.setText("Email: " + user.getEmail());
        username.setText("Username: " + user.getDisplayName());
        username.setText("User: " + user.getDisplayName());
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}


