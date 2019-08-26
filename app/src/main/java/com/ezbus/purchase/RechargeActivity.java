package com.ezbus.purchase;

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

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Classe che permette di ricaricare il credito del cliente.
 */

public class RechargeActivity extends AppCompatActivity {

    private TextView recharge;
    private Button confirm;
    private double amount = 5; //Ricarica iniziale consigliata
    private NumberFormat formatter = new DecimalFormat("#0.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        double myCredit = ProfileActivity.getClient().getMyPocket().getCredit();
        TextView credit = findViewById(R.id.credit);
        credit.setText(formatter.format(myCredit) + " €");
        recharge = findViewById(R.id.recharge);
        confirm = findViewById(R.id.confirm);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void changeCredit(Double c) {
        if (!(amount == 0 && c < 0)) {
            amount += c;
            recharge.setText(formatter.format(amount) + " €");
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