package com.ezbus.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Switch;

import com.ezbus.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()) {
            setTheme(R.style.App_Dark);
        }  else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch change_theme = findViewById(R.id.change_theme);
        if(sharedpref.loadNightModeState())
            change_theme.setChecked(true);

        change_theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sharedpref.setNightModeState(true);
                startNewActivity(SettingsActivity.class);
            } else {
                sharedpref.setNightModeState(false);
                startNewActivity(SettingsActivity.class);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(this, act);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startNewActivity(MainActivity.class);
        return true;
    }

    //Se viene premuto il pulsante Indietro di sistema
    @Override
    public void onBackPressed() {
        startNewActivity(MainActivity.class);
    }

}
