package com.ezbus.authentication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.main.SharedPref;

import java.io.IOException;
import java.io.InputStream;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        TextView txtPrivacy = findViewById(R.id.txtPrivacy);

        //genera il viewText dato un file txt (INCOMPLETO)
        try {
            InputStream is = getAssets().open("app/java/res/raw/privacy.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            //is.read(buffer);
            is.close();
            txtPrivacy.setText(new String(buffer));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}