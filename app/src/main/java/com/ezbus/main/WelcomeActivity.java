package com.ezbus.main;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ezbus.R;

/**
 * Schermata iniziale per la scelta al primo avvio.
 * L'ospite puo decidere se avviare l'applicazione come cliente o come azienda.
 */

public class WelcomeActivity extends AppCompatActivity {

    public static Boolean mLocationPermissionsGranted = false;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.NoApp_Dark);
        else setTheme(R.style.NoApp_Blue);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getLocationPermission();

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

    //Salva la preferenza di avvio dell'ospite
    private void setAnswer(String theAnswer) {
        SharedPreferences.Editor editor = getSharedPreferences("pref",0).edit();
        editor.putString("Scelta", theAnswer);
        editor.apply();
    }

    //Richiesta permessi di geolocalizzazione
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }



}