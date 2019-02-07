package com.ezbus.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ezbus.main.MainActivity;
import com.ezbus.R;

public class WelcomeActivity extends AppCompatActivity {

    Button company, client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setAnswer("Empty");
        if (getIntent().getBooleanExtra("EXIT", false)) finish();

        company = findViewById(R.id.scelta1);
        client = findViewById(R.id.scelta2);
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer("Client");
                startNewActivity(MainActivity.class);
            }
        });
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer("Company");
                startNewActivity(MainActivity.class);
            }
        });
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(this, act);
        startActivity(intent);
        finish();
    }

    public void setAnswer(String theAnswer) {
        SharedPreferences.Editor editor = getSharedPreferences("pref",0).edit();
        editor.putString("Scelta", theAnswer);
        editor.commit();
    }
}