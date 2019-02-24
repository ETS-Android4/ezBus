package com.ezbus.management;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity che permette all'azienda di aggiungere una nuova tratta al database.
 */

public class AddRouteActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter1, mAdapter2;
    private final ArrayList<String> idStop1 = new ArrayList<>();
    private final ArrayList<String> idStop2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        setDataToView();

        final EditText routeName = findViewById(R.id.nameRoute);
        final Spinner listStop1 = findViewById(R.id.spinner1);
        final Spinner listStop2 = findViewById(R.id.spinner2);

        Button addStop = findViewById(R.id.addStop);
        addStop.setOnClickListener(v -> {
            Intent intent = new Intent(AddRouteActivity.this, AddStopActivity.class);
            startActivity(intent);
        });

        Button saveRoute = findViewById(R.id.saveRoute);
        saveRoute.setOnClickListener(v -> {
            String companyId = LoginActivity.mAuth.getUid();
            //Controlla se i campi sono compilati
            if (!TextUtils.isEmpty(routeName.getText().toString().trim())) {
                addRoute(new Route(companyId, routeName.getText().toString().trim(), idStop1.get(listStop1.getSelectedItemPosition()),
                        idStop2.get(listStop2.getSelectedItemPosition())));
            }
            else {
                Toast.makeText(AddRouteActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> initialList1 = new ArrayList<>();
        List<String> initialList2 = new ArrayList<>();
        mAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList1);
        mAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList2);
        listStop1.setAdapter(mAdapter1);
        listStop2.setAdapter(mAdapter2);
    }

    //Aggiunge una nuova tratta al database
    private void addRoute(Route r) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        String uid = r.getId();
        rootRef.child("routes").child(uid).setValue(r);
        Intent intent = new Intent(AddRouteActivity.this, RouteManagerActivity.class);
        startActivity(intent);
        finish();
    }

    //Aggiorna la lista delle fermate
    private void setDataToView() {
        FirebaseDatabase.getInstance().getReference("/map").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter1.clear();
                mAdapter2.clear();
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren()) {
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Stop s = child.getValue(Stop.class);
                        mAdapter1.add(s.getName());
                        idStop1.add(s.getId());
                        mAdapter2.add(s.getName());
                        idStop2.add(s.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(AddRouteActivity.this, RouteManagerActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(AddRouteActivity.this, RouteManagerActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataToView();
    }

}