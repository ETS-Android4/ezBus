package com.ezbus.management;

import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.client.Pass;
import com.ezbus.main.SharedPref;
import com.ezbus.tracking.Position;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPassActivity extends AppCompatActivity {

    SharedPref sharedpref;
    EditText NameCompany;
    EditText NamePass;
    EditText NameCity;
    EditText NameType;
    EditText PricePass;
    private Pass newPass;


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

        this.NameCompany = findViewById(R.id.editText_nameCompany);
        this.NamePass = findViewById(R.id.editText_namePass);
        this.NameCity = findViewById(R.id.editText_nameCity);
        this.NameType = findViewById(R.id.editText_typePass);
        this.PricePass = findViewById(R.id.editText_pricePass);
        Button savePass = findViewById(R.id.save_pass);

        savePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyId = LoginActivity.mAuth.getUid();
                Pass pass = new Pass("coneroBus", "Loreto_urbano", "urbano", "Loreto", "78,00");
                addPass(pass);
                finish();

            }
        });
    }

    /*private void inputPass() {

        String companyId = LoginActivity.mAuth.getUid();

        String namecompany = NameCompany.getText().toString().trim();
        String namepass = NamePass.getText().toString().trim();
        String nametype = NameType.getText().toString().trim();
        String namecity = NameCity.getText().toString().trim();
        String pricepass = PricePass.getText().toString().trim();

        newPass = new Pass(namecompany, namepass, nametype, namecity, pricepass);

    }*/

    private void addPass(Pass p) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
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


    }

