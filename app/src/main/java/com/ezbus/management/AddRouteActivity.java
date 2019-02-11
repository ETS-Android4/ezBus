package com.ezbus.management;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.main.SharedPref;
import com.ezbus.tracking.Position;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddRouteActivity extends AppCompatActivity {

    SharedPref sharedpref;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private ArrayAdapter mAdapter1;
    private ArrayAdapter mAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final EditText routeName = findViewById(R.id.nameRoute);
        final Spinner listStop1 = findViewById(R.id.spinner1);
        final Spinner listStop2 = findViewById(R.id.spinner2);
        final ArrayList idStop1 = new ArrayList();
        final ArrayList idStop2 = new ArrayList();

        Button saveRoute = findViewById(R.id.saveRoute);
        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String companyId = LoginActivity.mAuth.getUid();
                /*Stop start = new Stop("Capolinea", new Position(43.1488538,13.0990438), companyId);
                Stop dest = new Stop("Farmacia", new Position(43.1763307,13.069168), companyId);
                addStop(start);
                addStop(dest);
                Track track = new Track("Corsa 1", "16:00");
                Stop s  = new Stop("Fermata Intermedia", new Position(43.1503307,13.082168), companyId);
                addStop(s);
                track.addStop(s);
                addTrack(track);
                Route route = new Route("Linea R", companyId, start.getId(), dest.getId());
                addRoute(route);*/
                addRoute(new Route(routeName.getText().toString().trim(), companyId, idStop1.get(listStop1.getSelectedItemPosition()).toString(),
                        idStop2.get(listStop2.getSelectedItemPosition()).toString()));
                finish();
            }
        });

        List<String> initialList1 = new ArrayList<String>();
        List<String> initialList2 = new ArrayList<String>();
        mAdapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, initialList1);
        mAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, initialList2);
        listStop1.setAdapter(mAdapter1);
        listStop2.setAdapter(mAdapter2);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter1.clear();
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren()) {
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Stop s = child.getValue(Stop.class);
                        mAdapter1.add(s.getName());
                        idStop1.add(s.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter2.clear();
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren()) {
                    if (child.child("companyId").getValue().equals(LoginActivity.mAuth.getCurrentUser().getUid())) {
                        Stop s = child.getValue(Stop.class);
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

    private void addRoute(Route r) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            String uid = r.getId();
            rootRef.child("routes").child(uid).setValue(r);
        }
    }

    private void addStop(Stop s) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            String uid = s.getId();
            rootRef.child("stops").child(uid).setValue(s);
        }
    }

    private void addTrack(Track t) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            String uid = t.getId();
            rootRef.child("tracks").child(uid).setValue(t);
        }
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
}