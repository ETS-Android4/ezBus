package com.ezbus.management;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.main.SharedPref;
import com.ezbus.tracking.Position;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity che permette all'azienda di aggiungere una nuova fermata al database.
 */

public class AddStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText nameStop = findViewById(R.id.nameStop);
        final EditText lat = findViewById(R.id.latPosition);
        final EditText lon = findViewById(R.id.lonPosition);

        Button saveStop = findViewById(R.id.saveStop);
        saveStop.setOnClickListener(v -> {
            String companyId = LoginActivity.getCurrentUser().getUid();
            //Controlla se i campi sono stati compilati
            if (!TextUtils.isEmpty(nameStop.getText().toString().trim()) &&
                    !TextUtils.isEmpty(lat.getText().toString().trim()) &&
                    !TextUtils.isEmpty(lon.getText().toString().trim())) {
                addStop(new Stop( companyId, new Position(Double.parseDouble(lat.getText().toString()),
                        Double.parseDouble(lon.getText().toString())), nameStop.getText().toString()));
                finish();
            }
            else Toast.makeText(AddStopActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
        });
    }

    //Aggiunge una nuova fermata al database
    private void addStop(Stop s) {
        String uid = s.getId();
        FirebaseDatabase.getInstance().getReference("/map/stops/"+uid).setValue(s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}