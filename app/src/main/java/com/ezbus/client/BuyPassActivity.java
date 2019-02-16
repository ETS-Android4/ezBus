package com.ezbus.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuyPassActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private final ArrayList<String> idPass = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pass);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView listPass = findViewById(R.id.buyable_pass);
        List<String> initialList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, R.layout.row, R.id.textViewList, initialList);
        listPass.setAdapter(mAdapter);
        listPass.setOnItemClickListener((parent, view, position, id) -> database.addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.child("pass").getChildren()) {
                    if (child.child("id").getValue().toString().equals(idPass.get(position))) {
                        Pass newPass = child.getValue(Pass.class);
                        List<Pass> myPasses = ProfileActivity.getClient().getMyPocket().getMyPass();
                        Boolean trovato = false;
                        for (Pass abbonamento : myPasses) {
                            if (abbonamento.getId().equals(newPass.getId())) {
                                //Andiamo a cercare se è gia presente un abbonamento con quell'id
                                trovato = true;
                                break;
                            }
                        }
                        if (!trovato) {
                            //Andiamo a vedere se il credito è sufficiente
                            Double myCredit = ProfileActivity.getClient().getMyPocket().getCredit();
                            Double passPrice = newPass.getPrice();
                            if (myCredit >= newPass.getPrice()) {
                                ProfileActivity.getClient().getMyPocket().updateCredit(-passPrice);
                                ProfileActivity.getClient().getMyPocket().addPass(newPass);
                                Toast.makeText(getApplicationContext(),"Abbonamento acquistato",Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(getApplicationContext(),"Credito insufficiente per l'operazione",Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(getApplicationContext(),"Già possiedi questo abbonamento",Toast.LENGTH_SHORT).show();
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
                for (DataSnapshot child : dataSnapshot.child("pass").getChildren()) {
                    Pass p = child.getValue(Pass.class);
                    if (p!=null) {
                        mAdapter.add(p.getName());
                        idPass.add(p.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}