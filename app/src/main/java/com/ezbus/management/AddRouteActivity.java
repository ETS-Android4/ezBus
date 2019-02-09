package com.ezbus.management;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.main.SharedPref;
import com.ezbus.tracking.Position;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRouteActivity extends AppCompatActivity {

    SharedPref sharedpref;


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

        Button saveRoute = findViewById(R.id.saveRoute);
        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String companyId = LoginActivity.mAuth.getUid();
            Stop start = new Stop("Capolinea", new Position(43.1488538,13.0990438), companyId);
            Stop dest = new Stop("Farmacia", new Position(43.1763307,13.069168), companyId);
            addStop(start);
            addStop(dest);
            Track track = new Track("Corsa 1", "16:00");
            Stop s  = new Stop("Fermata Intermedia", new Position(43.1503307,13.082168), companyId);
            addStop(s);
            track.addStop(s);
            addTrack(track);
            Route route = new Route("Linea R", companyId, start.getId(), dest.getId());
            addRoute(route);
            finish();
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