package com.ezbus.purchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.ezbus.management.Route;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che permette l'acquisto di tessere.
 */

public class BuyCardActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("/routes");
    private final ArrayList<String> idRoute = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

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
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.child("id").getValue().toString().equals(idRoute.get(position))) {
                            String idCompany = child.child("companyId").getValue().toString();
                            Route route = child.getValue(Route.class);
                            //Creazione nuova tessera in base all'id della route selezionata
                            Card newCard = new Card(idCompany, 30, route.getId(), route.getName());
                            Intent intent = new Intent(getApplicationContext(), ViewDocumentActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Document", newCard);
                            intent.putExtras(bundle);
                            intent.putExtra("Buy", true);
                            startActivity(intent);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }));

        setDataToView();
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

    private void setDataToView() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mAdapter.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Route r = child.getValue(Route.class);
                    if (r!=null) {
                        //Ogni volta che viene aggiunto un elemento all'array viene aggiunto anche alla lista
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