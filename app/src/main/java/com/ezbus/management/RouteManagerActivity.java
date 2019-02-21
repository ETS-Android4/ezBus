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
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RouteManagerActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final ArrayList<String> idRoute1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_manager);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView listRoute = findViewById(R.id.list_routes);
        List<String> initialList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.textViewList, initialList);
        listRoute.setAdapter(mAdapter);
        listRoute.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RouteManagerActivity.this, EditRouteActivity.class);
            intent.putExtra("Route", idRoute1.get(position));
            startActivity(intent);
        });

        aggiornaDati();

        Button addRoute = findViewById(R.id.addRoute);
        addRoute.setOnClickListener(v -> {
            Intent intent = new Intent(RouteManagerActivity.this, AddRouteActivity.class);
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
        aggiornaDati();
    }

    private void aggiornaDati() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for (DataSnapshot child : dataSnapshot.child("routes").getChildren()) {
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Route r = child.getValue(Route.class);
                        mAdapter.add(r.getName());
                        idRoute1.add(r.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}