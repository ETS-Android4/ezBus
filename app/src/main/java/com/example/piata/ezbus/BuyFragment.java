package com.example.piata.ezbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class BuyFragment extends Fragment implements View.OnClickListener {

    TextView text;
    View view;


    public BuyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        text = view.findViewById(R.id.text_buy);
        text.setText("Ora puoi acquistare");
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            text.setText("Non puoi acquistare");
        } else {
            text.setText("Ora puoi acquistare");
        }
    }

}
