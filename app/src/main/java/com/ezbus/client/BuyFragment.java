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
import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BuyFragment extends Fragment {

    View view;
    private static TextView credit;


    public BuyFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);

        Button buttonPass = view.findViewById(R.id.buyPass);
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(BuyPassActivity.class);
            }
        });

        Button buttonCredit = view.findViewById(R.id.recharge);
        buttonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewActivity(RechargeActivity.class);
            }
        });

        credit = view.findViewById(R.id.credit);
        setCredit(LoginActivity.mAuth.getCurrentUser());

        return view;
    }

    private void setCredit(FirebaseUser newUser) {
        Query search = FirebaseDatabase.getInstance().getReference().child("clients").child(newUser.getUid()).child("myPocket").child("credit");
        search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setDataToView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static void setDataToView() {
        double myCredit = ProfileActivity.getClient().getMyPocket().getCredit();
        credit.setText(Double.toString(myCredit) + " €");
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(getContext(), act);
        startActivity(intent);
    }

}