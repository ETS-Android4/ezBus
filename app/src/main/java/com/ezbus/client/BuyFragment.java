package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Fragment per la gestione degli acquisti.
 */

public class BuyFragment extends Fragment {

    private TextView credit;


    public BuyFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        Button buttonCard = view.findViewById(R.id.buyCard);
        buttonCard.setOnClickListener(v -> startNewActivity(BuyCardActivity.class));
        Button buttonPass = view.findViewById(R.id.buyPass);
        buttonPass.setOnClickListener(v -> startNewActivity(BuyPassActivity.class));
        Button buttonCredit = view.findViewById(R.id.recharge);
        buttonCredit.setOnClickListener(v -> startNewActivity(RechargeActivity.class));

        credit = view.findViewById(R.id.credit);
        setCredit(LoginActivity.mAuth.getCurrentUser());

        return view;
    }

    //Prende dal database il credito del cliente
    private void setCredit(FirebaseUser newUser) {
        FirebaseDatabase.getInstance().getReference().child("clients").child(newUser.getUid()).child("myPocket").child("credit")
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setDataToView(dataSnapshot.getValue(Double.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setDataToView(Double credit) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        this.credit.setText(formatter.format(credit) + " €");
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(getContext(), act);
        startActivity(intent);
    }

}