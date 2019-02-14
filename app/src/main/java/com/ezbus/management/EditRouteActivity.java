package com.ezbus.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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

public class EditRouteActivity extends AppCompatActivity implements MyCallback {

    SharedPref sharedpref;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private ArrayAdapter<String> mAdapter1;
    private ArrayAdapter<String> mAdapter2;
    private EditText routeName;
    private Spinner listStop1, listStop2;
    final ArrayList<String> idStop1 = new ArrayList<>();
    final ArrayList<String> idStop2 = new ArrayList<>();
    private String idRoute;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        idRoute = getIntent().getSerializableExtra("Route").toString();
        aggiornaDati();

        routeName = findViewById(R.id.nomeTratta);
        listStop1 = findViewById(R.id.scelta1);
        listStop2 = findViewById(R.id.scelta2);

        Button addStop = findViewById(R.id.aggiungiFermata);
        addStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditRouteActivity.this, AddStopActivity.class);
                startActivity(intent);
            }
        });

        Button saveRoute = findViewById(R.id.salvaTratta);
        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editRoute();
            }
        });

        Button delRoute = findViewById(R.id.eliminaTratta);
        delRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoute();
            }
        });

        List<String> initialList1 = new ArrayList<>();
        List<String> initialList2 = new ArrayList<>();
        mAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList1);
        mAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, initialList2);
        listStop1.setAdapter(mAdapter1);
        listStop2.setAdapter(mAdapter2);
    }

    private void editRoute() {
        if (!TextUtils.isEmpty(routeName.getText().toString().trim())) saveRoute();
        else Toast.makeText(EditRouteActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
    }

    private void deleteRoute() {
        AlertDialog.Builder logout = new AlertDialog.Builder(EditRouteActivity.this);
        logout.setMessage("Vuoi davvero eliminare la tratta?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.child("routes").child(idRoute).removeValue();
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Operazione annullata
            }
        });
        logout.show();
    }


    private void saveRoute() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("routes").child(idRoute).child("name").setValue(routeName.getText().toString().trim());
        rootRef.child("routes").child(idRoute).child("start").setValue(idStop1.get(listStop1.getSelectedItemPosition()));
        rootRef.child("routes").child(idRoute).child("end").setValue(idStop2.get(listStop2.getSelectedItemPosition()));
        finish();
    }

    private void aggiornaDati() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter1.clear();
                mAdapter2.clear();
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren())
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Stop s = child.getValue(Stop.class);
                            mAdapter1.add(s.getName());
                            idStop1.add(s.getId());
                            mAdapter2.add(s.getName());
                            idStop2.add(s.getId());
                    }
                for (DataSnapshot child : dataSnapshot.child("routes").getChildren()) {
                    if (child.child("id").getValue().equals(idRoute)) {
                        routeName.setText(child.child("name").getValue().toString());
                        String idStart = child.child("start").getValue().toString();
                        String idDest = child.child("end").getValue().toString();
                        getNameStop(1, idStart, new MyCallback() {
                            @Override
                            public void onCallback(String value) {
                                listStop1.setSelection(mAdapter1.getPosition(value));
                            }
                        });
                        getNameStop(2, idDest, new MyCallback() {
                            @Override
                            public void onCallback(String value) {
                                listStop2.setSelection(mAdapter2.getPosition(value));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getNameStop(final int i, final String idStop, final MyCallback myCallback) {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren()) {
                    if (child.child("id").getValue().equals(idStop)) {
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
        aggiornaDati();
    }


    @Override
    public void onCallback(String value) {

    }
}
