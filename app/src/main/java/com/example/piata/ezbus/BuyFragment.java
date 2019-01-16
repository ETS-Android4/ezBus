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

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment implements View.OnClickListener {

    Button buttonLogin;
    TextView text;
    View view;

    public BuyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        buttonLogin = view.findViewById(R.id.butLogin);
        text = view.findViewById(R.id.text_buy);
        /*if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
            buttonLogin.setVisibility(View.VISIBLE);
            text.setText("Non puoi acquistare");
        } else {*/
            buttonLogin.setVisibility(View.GONE);
            text.setText("Ora puoi acquistare");
        //}
        buttonLogin.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butLogin:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (FirebaseAuth.getInstance().getCurrentUser()==null) {
            buttonLogin.setVisibility(View.VISIBLE);
            text.setText("Non puoi acquistare");
        } else {
            buttonLogin.setVisibility(View.GONE);
            text.setText("Ora puoi acquistare");
        }
    }

}
