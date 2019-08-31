package com.ezbus.management;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
 * Activity che permette all'azienda di modificare le proprie tratte.
 */

public class EditRouteActivity extends AppCompatActivity implements MyCallback {

    private ArrayAdapter<String> mAdapter1, mAdapter2;
    private EditText routeName;
    private Spinner listStop1, listStop2;
    private final ArrayList<String> idStop1 = new ArrayList<>();
    private final ArrayList<String> idStop2 = new ArrayList<>();
    private String idRoute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        idRoute = getIntent().getSerializableExtra("Route").toString();
        routeName = findViewById(R.id.nomeTratta);
        listStop1 = findViewById(R.id.scelta1);
        listStop2 = findViewById(R.id.scelta2);

        Button addStop = findViewById(R.id.aggiungiFermata);
        addStop.setOnClickListener(v -> {
            Intent intent = new Intent(EditRouteActivity.this, AddStopActivity.class);
            startActivity(intent);
        });

        Button saveRoute = findViewById(R.id.salvaTratta);
        saveRoute.setOnClickListener(v -> editRoute());

        Button delRoute = findViewById(R.id.eliminaTratta);
        delRoute.setOnClickListener(v -> deleteRoute());

        List<String> initialList1 = new ArrayList<>();
        List<String> initialList2 = new ArrayList<>();
        mAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList1);
        mAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList2);
        listStop1.setAdapter(mAdapter1);
        listStop2.setAdapter(mAdapter2);
        setDataToView();
    }

    //Permette la modifica di una tratta
    private void editRoute() {
        if (!TextUtils.isEmpty(routeName.getText().toString().trim())) saveRoute();
        else Toast.makeText(EditRouteActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
    }

    //Permette la rimozione di una tratta dal database
    private void deleteRoute() {
        AlertDialog.Builder logout = new AlertDialog.Builder(EditRouteActivity.this);
        logout.setMessage("Vuoi davvero eliminare la tratta?").setPositiveButton("Si", (dialog, id) -> {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            rootRef.child("routes").child(idRoute).removeValue();
            finish();
        }).setNegativeButton("No", (dialog, id) -> {
            //Operazione annullata
        });
        logout.show();
    }

    //Salva le modifiche effettuate nel database
    private void saveRoute() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("/routes/"+idRoute);
        rootRef.child("name").setValue(routeName.getText().toString().trim());
        rootRef.child("start").setValue(idStop1.get(listStop1.getSelectedItemPosition()));
        rootRef.child("end").setValue(idStop2.get(listStop2.getSelectedItemPosition()));
        finish();
    }

    //Carica i dati della tratta da modificare
    private void setDataToView() {
        FirebaseDatabase.getInstance().getReference("/map/stops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter1.clear();
                mAdapter2.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("idCompany").getValue().equals(LoginActivity.getCurrentUser().getUid())) {
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
        FirebaseDatabase.getInstance().getReference("/routes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("id").getValue().equals(idRoute)) {
                        routeName.setText(child.child("name").getValue().toString());
                        String idStart = child.child("start").getValue().toString();
                        String idDest = child.child("end").getValue().toString();
                        getNameStop(1, idStart, value -> listStop1.setSelection(mAdapter1.getPosition(value)));
                        getNameStop(2, idDest, value -> listStop2.setSelection(mAdapter2.getPosition(value)));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Restituisce il nome della fermata dato il suo id
    private void getNameStop(final int i, final String idStop, final MyCallback myCallback) {
        FirebaseDatabase.getInstance().getReference("/map/stops").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("id").getValue().equals(idStop)) {
                        //Con myCallback viene estratto qualsiasi valore all'interno del onDataChange
                        if (i==1) myCallback.onCallback(child.child("name").getValue().toString());
                        if (i==2) myCallback.onCallback(child.child("name").getValue().toString());
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
        finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataToView();
    }

    @Override
    public void onCallback(String value) {

    }

}