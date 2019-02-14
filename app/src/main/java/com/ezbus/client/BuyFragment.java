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
import com.ezbus.authentication.Client;
import com.ezbus.authentication.ProfileActivity;

public class BuyFragment extends Fragment {

    View view;
    TextView credit;


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
        updateData();

        return view;
    }

    @Override
    public void onResume() {
        updateData();
        super.onResume();
    }

    private void updateData() {
        double myCredit = ((Client) ProfileActivity.getUser()).getMyPocket().getCredit();
        credit.setText(Double.toString(myCredit) + " €");
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(getContext(), act);
        startActivity(intent);
    }

}