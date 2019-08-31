package com.ezbus.management;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.purchase.Pass;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity che permette all'azienda di aggiungere un nuovo abbonamento al database.
 */

public class AddPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText namePass = findViewById(R.id.editText_namePass);
        final EditText nameCity = findViewById(R.id.editText_nameCity);
        final EditText nameDays = findViewById(R.id.editText_nameDays);
        final EditText pricePass = findViewById(R.id.editText_pricePass);

        Button savePass = findViewById(R.id.save_pass);
        savePass.setOnClickListener(view -> {
            String idCompany = LoginActivity.getCurrentUser().getUid();
            //Controlla se i campi sono stati compilati
            if (!TextUtils.isEmpty(namePass.getText().toString().trim()) &&
                    !TextUtils.isEmpty(nameCity.getText().toString().trim()) &&
                    !TextUtils.isEmpty(nameDays.getText().toString().trim()) &&
                    !TextUtils.isEmpty(pricePass.getText().toString().trim())) {

                double price = Double.parseDouble(pricePass.getText().toString().trim());
                int expiration = Integer.parseInt(nameDays.getText().toString().trim());
                String name = namePass.getText().toString().trim();
                String city = nameCity.getText().toString().trim();
                addPass(new Pass(idCompany, price, expiration, name, city));

            }
            else Toast.makeText(AddPassActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
        });
    }

    //Aggiunge il nuovo pass al database
    private void addPass(Pass p) {
        String uid = p.getId();
        FirebaseDatabase.getInstance().getReference("/pass/"+uid).setValue(p);
        Intent intent = new Intent(AddPassActivity.this, PassManagerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(AddPassActivity.this, PassManagerActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(AddPassActivity.this, PassManagerActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}