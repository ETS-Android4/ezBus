package com.ezbus.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Classe che permette l'acquisto di biglietti.
 */

public class BuyTicketActivity extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("/map/stops");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);

        setDataToView();
    }

    private void setDataToView() {
        //Prende i dati passati dall'activity precedente
        String idStart = getIntent().getSerializableExtra("Start").toString();
        String idDest = getIntent().getSerializableExtra("Dest").toString();
        String nameStart = getIntent().getSerializableExtra("StartName").toString();
        String nameDest = getIntent().getSerializableExtra("DestName").toString();
        TextView start = findViewById(R.id.partenza);
        TextView dest = findViewById(R.id.destinazione);
        start.setText(nameStart);
        dest.setText(nameDest);
        Button search = findViewById(R.id.search);
        search.setOnClickListener(v -> database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    if (child.child("id").getValue().toString().equals(idStart)) {
                        String idCompany = child.child("companyId").getValue().toString();
                        //Creazione del biglietto in base alle fermate scelte
                        Ticket newTicket = new Ticket(idCompany, idStart, idDest, nameStart + " - " + nameDest);
                        Intent intent = new Intent(getApplicationContext(), ViewDocumentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Document", newTicket);
                        intent.putExtras(bundle);
                        intent.putExtra("Buy", true);
                        startActivity(intent);
                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}