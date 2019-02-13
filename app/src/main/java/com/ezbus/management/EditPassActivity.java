package com.ezbus.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
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
import com.ezbus.main.SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPassActivity extends AppCompatActivity {

    SharedPref sharedpref;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private String idPass;
    private EditText namePass, cityPass, typePass, pricePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        idPass = getIntent().getSerializableExtra("Pass").toString();

        namePass = findViewById(R.id.nomePass);
        pricePass = findViewById(R.id.prezzoPass);
        cityPass = findViewById(R.id.cittaPass);
        typePass = findViewById(R.id.tipoPass);

        aggiornaDati();

        Button saveRoute = findViewById(R.id.salvaPass);
        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePass();
            }
        });
        Button deletePass = findViewById(R.id.cancellaPass);
        deletePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePass();
            }
        });
    }

    private void removePass() {
        AlertDialog.Builder logout = new AlertDialog.Builder(EditPassActivity.this);
        logout.setMessage("Vuoi davvero eliminare l'abbonamento?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.child("pass").child(idPass).removeValue();
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Operaione annullata
            }
        });
        logout.show();
    }


    private void savePass() {
        if (!TextUtils.isEmpty(namePass.getText().toString().trim()) && !TextUtils.isEmpty(pricePass.getText().toString().trim()) &&
        !TextUtils.isEmpty(typePass.getText().toString().trim()) && !TextUtils.isEmpty(cityPass.getText().toString().trim())) {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser user = auth.getCurrentUser();
            if (user!=null) {
                rootRef.child("pass").child(idPass).child("name").setValue(namePass.getText().toString().trim());
                rootRef.child("pass").child(idPass).child("price").setValue(Double.parseDouble(pricePass.getText().toString().trim()));
                rootRef.child("pass").child(idPass).child("type").setValue(typePass.getText().toString().trim());
                rootRef.child("pass").child(idPass).child("city").setValue(cityPass.getText().toString().trim());
            }
            finish();
        }
        else Toast.makeText(EditPassActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
    }

    private void aggiornaDati() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("pass").getChildren()) {
                    if (child.child("id").getValue().equals(idPass)) {
                        namePass.setText(child.child("name").getValue().toString());
                        pricePass.setText(child.child("price").getValue().toString());
                        cityPass.setText(child.child("city").getValue().toString());
                        typePass.setText(child.child("type").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
