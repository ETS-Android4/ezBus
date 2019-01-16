package com.example.piata.ezbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {



        private TextView email;
        private TextView username;
        private TextView name;
        private TextView surname;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            email = (TextView) findViewById(R.id.Email);
            username = (TextView) findViewById(R.id.Username);
            //name = (TextView) findViewById(R.id.Name);
            //surname = (TextView) findViewById(R.id.Surname);

            //get firebase auth instance
            //auth = FirebaseAuth.getInstance();

            //get current user
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            setDataToView(user);
        }

        @SuppressLint("SetTextI18n")
        private void setDataToView(FirebaseUser user) {
            email.setText("Email: " + user.getEmail());
            username.setText("Username: " + user.getDisplayName());
            username.setText("User: " + user.getDisplayName());
        }
    }


