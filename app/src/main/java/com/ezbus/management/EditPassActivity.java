package com.ezbus.management;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Activity che permette all'azienda di modificare i propri abbonamenti.
 */

public class EditPassActivity extends AppCompatActivity {

    private String idPass;
    private EditText namePass, cityPass, dataPass, pricePass;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("/pass");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        idPass = getIntent().getSerializableExtra("Pass").toString();

        namePass = findViewById(R.id.nomePass);
        pricePass = findViewById(R.id.prezzoPass);
        cityPass = findViewById(R.id.cittaPass);
        dataPass = findViewById(R.id.dataPass);

        setDataToView();

        Button saveRoute = findViewById(R.id.salvaPass);
        saveRoute.setOnClickListener(v -> savePass());
        Button deletePass = findViewById(R.id.cancellaPass);
        deletePass.setOnClickListener(v -> removePass());
    }

    //Permette la rimozione dell'abbonamento dal database
    private void removePass() {
        AlertDialog.Builder logout = new AlertDialog.Builder(EditPassActivity.this);
        logout.setMessage("Vuoi davvero eliminare l'abbonamento?").setPositiveButton("Si", (dialog, id) -> {
            database.child(idPass).removeValue();
            finish();
        }).setNegativeButton("No", (dialog, id) -> {
            //Operaione annullata
        });
        logout.show();
    }

    //Salva le modifiche effettuate nel database
    private void savePass() {
        if (!TextUtils.isEmpty(namePass.getText().toString().trim()) && !TextUtils.isEmpty(pricePass.getText().toString().trim()) &&
        !TextUtils.isEmpty(dataPass.getText().toString().trim()) && !TextUtils.isEmpty(cityPass.getText().toString().trim())) {
            database.child(idPass).child("name").setValue(namePass.getText().toString().trim());
            database.child(idPass).child("price").setValue(Double.parseDouble(pricePass.getText().toString().trim()));
            database.child(idPass).child("expiration").setValue(Integer.parseInt(dataPass.getText().toString().trim()));
            database.child(idPass).child("city").setValue(cityPass.getText().toString().trim());
            finish();
        }
        else Toast.makeText(EditPassActivity.this, "Devi compilare tutti i campi", Toast.LENGTH_SHORT).show();
    }

    //Carica i dati dell'abbonamento da modificare
    private void setDataToView() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("id").getValue().equals(idPass)) {
                        namePass.setText(child.child("name").getValue().toString());
                        pricePass.setText(child.child("price").getValue().toString());
                        cityPass.setText(child.child("city").getValue().toString());
                        dataPass.setText(child.child("expiration").getValue().toString());
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