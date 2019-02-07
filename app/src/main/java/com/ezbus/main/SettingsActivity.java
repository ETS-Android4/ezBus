package com.ezbus.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.ezbus.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch change_theme;
    SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);

        if(sharedpref.loadNightModeState()) {
            setTheme(R.style.App_Dark);
        }  else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        change_theme = findViewById(R.id.change_theme);
        if(sharedpref.loadNightModeState())
            change_theme.setChecked(true);

        change_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sharedpref.setNightModeState(true);
                    startNewActivity(SettingsActivity.class);
                } else {
                    sharedpref.setNightModeState(false);
                    startNewActivity(SettingsActivity.class);
                }
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
