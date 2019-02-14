package com.ezbus.client;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.Client;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RechargeActivity extends AppCompatActivity {

    TextView credit, recharge;
    Button plus, minus;
    int r;
    double myCredit = ((Client) ProfileActivity.getUser()).getMyPocket().getCredit();
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
        credit.setText(Double.toString(myCredit));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    void changeCredit(int c) {
        if (!(r == 0 && c < 0)) {
            r += c;
            recharge.setText(Integer.toString(r));
        }
    }

    void updateCredit(double newCredit) {
        credit.setText(Double.toString(newCredit));
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("clients").child(ProfileActivity.getUser().getUid()).child("myPocket").child("credit").setValue(newCredit);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plus:
                changeCredit(1);
                break;
            case R.id.minus:
                changeCredit(-1);
                break;
            case R.id.confirm:
                updateCredit(this.myCredit + r);
        }
    }

}