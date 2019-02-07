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

    Button scelta1, scelta2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setAnswer("Empty");
        if (getIntent().getBooleanExtra("EXIT", false)) finish();

        scelta1 = findViewById(R.id.scelta1);
        scelta2 = findViewById(R.id.scelta2);
        scelta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer("Client");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        scelta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswer("Company");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void setAnswer(String theAnswer) {
        SharedPreferences.Editor editor = getSharedPreferences("pref",0).edit();
        editor.putString("Scelta", theAnswer);
        editor.commit();
    }
}