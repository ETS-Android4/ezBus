package com.ezbus.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ezbus.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setAnswer("Empty");
        if (getIntent().getBooleanExtra("EXIT", false)) finish();

        Button client = findViewById(R.id.client);
        Button company = findViewById(R.id.company);
        client.setOnClickListener(v -> {
            setAnswer("Client");
            startNewActivity();
        });
        company.setOnClickListener(v -> {
            setAnswer("Company");
            startNewActivity();
        });
    }

    private void startNewActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setAnswer(String theAnswer) {
        SharedPreferences.Editor editor = getSharedPreferences("pref",0).edit();
        editor.putString("Scelta", theAnswer);
        editor.apply();
    }

}