package com.ezbus.client;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;

public class RechargeActivity extends AppCompatActivity {

    private TextView credit, recharge;
    private int amount;
    SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        credit = findViewById(R.id.credit);
        credit.setText(Double.toString(ProfileActivity.getClient().getMyPocket().getCredit()));
        recharge = findViewById(R.id.recharge);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    void changeCredit(int c) {
        if (!(amount == 0 && c < 0)) {
            amount += c;
            recharge.setText(Integer.toString(amount));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plus:
                changeCredit(1);
                break;
            case R.id.minus:
                changeCredit(-1);
                break;
            case R.id.confirm:
                ProfileActivity.getClient().getMyPocket().updateCredit(amount);
                finish();
                break;
        }
    }

}