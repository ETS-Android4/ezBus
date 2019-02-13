package com.ezbus.management;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.tracking.Position;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText nameStop = findViewById(R.id.nameStop);
        final EditText lat = findViewById(R.id.latPosition);
        final EditText lon = findViewById(R.id.lonPosition);

        Button saveStop = findViewById(R.id.saveStop);
        saveStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyId = LoginActivity.mAuth.getUid();
                if (!TextUtils.isEmpty(nameStop.getText().toString().trim()) &&
                        !TextUtils.isEmpty(lat.getText().toString().trim()) &&
                        !TextUtils.isEmpty(lon.getText().toString().trim())) {
                    addStop(new Stop(nameStop.getText().toString(), new Position(Double.parseDouble(lat.getText().toString()),
                            Double.parseDouble(lon.getText().toString())), companyId));
                    finish();
                }
                else Toast.makeText(AddStopActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addStop (Stop s) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = s.getId();
        rootRef.child("stops").child(uid).setValue(s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}