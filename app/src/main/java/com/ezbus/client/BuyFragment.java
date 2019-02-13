package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;

public class BuyFragment extends Fragment {

    View view;
    TextView text;


    public BuyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);

        Button buttonPass = view.findViewById(R.id.buyPass);
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BuyPassActivity.class);
                startActivity(intent);
            }
        });

        Button buttonCredit = view.findViewById(R.id.recharge);
        buttonCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RechargeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
