package com.ezbus.client;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DatabaseReference;

public class RechargeActivity extends AppCompatActivity {

    TextView credit, recharge;
    Button plus, minus, confirm;
    double amount = 5.0; //Ricarica consigliata iniziale
    double myCredit = ProfileActivity.getClient().getMyPocket().getCredit();
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
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        recharge = findViewById(R.id.recharge);
        credit.setText(Double.toString(myCredit) + " €");
        confirm = findViewById(R.id.confirm);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    void changeCredit(Double c) {
        if (!(amount == 0 && c < 0)) {
            amount += c;
            recharge.setText(Double.toString(amount) + " €");
            if (amount==0) confirm.setEnabled(false);
            else confirm.setEnabled(true);
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
                changeCredit(1.0);
                break;
            case R.id.minus:
                changeCredit(-1.0);
                break;
            case R.id.confirm:
                ProfileActivity.getClient().getMyPocket().updateCredit(amount);
                finish();
                break;
        }
    }

}