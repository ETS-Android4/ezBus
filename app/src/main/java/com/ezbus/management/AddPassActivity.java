package com.ezbus.management;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.client.Pass;
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
import java.util.UUID;

public class AddPassActivity extends AppCompatActivity {

    SharedPref sharedpref;
    private Pass newPass;
    private ArrayAdapter mAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);



        Button savePass = findViewById(R.id.save_pass);
        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyId = LoginActivity.mAuth.getUid();
                final EditText namePass = findViewById(R.id.editText_namePass);
                final EditText nameCity = findViewById(R.id.editText_nameCity);
                final EditText nameType = findViewById(R.id.editText_typePass);
                final EditText pricePass = findViewById(R.id.editText_pricePass);


                addPass(new Pass(companyId, namePass, nameCity, nameType, pricePass));
                finish();



            }
        });

    }


    private void addPass(Pass p) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = p.getCompanyId();
            rootRef.child("pass").child(uid).setValue(p);
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

    @Override
    protected void onResume() {
        super.onResume();

    }


}

