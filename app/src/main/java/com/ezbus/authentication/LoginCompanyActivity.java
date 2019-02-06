package com.ezbus.authentication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.firebase.auth.FirebaseAuth;

public class LoginCompanyActivity extends AppCompatActivity {

    SharedPref sharedpref;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true)
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);
        setContentView(R.layout.activity_login_company);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
