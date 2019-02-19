package com.ezbus.client;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;
import com.ezbus.management.Route;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyCardActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final ArrayList<String> idRoute = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_card);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView listPass = findViewById(R.id.buyable_card);
        List<String> initialList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.textViewList, initialList);
        listPass.setAdapter(mAdapter);
        listPass.setOnItemClickListener((parent, view, position, id) -> database.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.child("routes").getChildren()) {
                            if (child.child("id").getValue().toString().equals(idRoute.get(position))) {
                                String idCompany = child.child("companyId").getValue().toString();
                                Route newRoute = child.getValue(Route.class);
                                Card newCard = new Card(idCompany, 30, newRoute.getId());
                                List<Card> myCards = ProfileActivity.getClient().getMyPocket().getMyCards();
                                boolean trovato = false;
                                for (Card tessera : myCards) {
                                    if (tessera.getIdRoute().equals(newRoute.getId())) {
                                        //Andiamo a cercare se è gia presente un abbonamento con quell'id
                                        trovato = true;
                                        break;
                                    }
                                }
                                if (!trovato) {
                                    //Andiamo a vedere se il credito è sufficiente
                                    double myCredit = ProfileActivity.getClient().getMyPocket().getCredit();
                                    double cardPrice = newCard.getPrice();
                                    if (myCredit >= cardPrice) {
                                        ProfileActivity.getClient().getMyPocket().addCard(newCard);
                                        Toast.makeText(getApplicationContext(),"Tessera acquistata",Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(getApplicationContext(),"Credito insufficiente per l'operazione",Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(getApplicationContext(),"Già possiedi questa tessera",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }));

        aggiornaDati();
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
                    Route r = child.getValue(Route.class);
                    if (r!=null) {
                        mAdapter.add(r.getName());
                        idRoute.add(r.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
