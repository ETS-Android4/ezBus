package com.ezbus.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.client.Pass;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity per la gestione degli abbonamenti da parte dell'azienda.
 */

public class PassManagerActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final ArrayList<String> idPass = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_manager);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView listPass = findViewById(R.id.list_pass);
        List<String> initialList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.textViewList, initialList);
        listPass.setAdapter(mAdapter);
        listPass.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(PassManagerActivity.this, EditPassActivity.class);
            intent.putExtra("Pass", idPass.get(position));
            startActivity(intent);
        });

        setDataToView();

        Button addPass = findViewById(R.id.addPass);
        addPass.setOnClickListener(v -> {
            Intent intent = new Intent(PassManagerActivity.this, AddPassActivity.class);
            startActivity(intent);
            finish();
        });
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

    //Aggiorna la lista degli abbonamenti
    private void setDataToView() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for (DataSnapshot child : dataSnapshot.child("pass").getChildren()) {
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Pass p = child.getValue(Pass.class);
                        if (p != null) {
                            mAdapter.add(p.getName());
                            idPass.add(p.getId());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}