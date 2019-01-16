package com.example.piata.ezbus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PocketFragment extends Fragment implements View.OnClickListener {

    Button buttonLogin;
    TextView text;
    View view;

    public PocketFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pocket, container, false);
        buttonLogin = view.findViewById(R.id.butLogin);
        text = view.findViewById(R.id.text_pocket);
        /*if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
            buttonLogin.setVisibility(View.VISIBLE);
            text.setText("Devi fare il login");

        } else {*/
            buttonLogin.setVisibility(View.GONE);
            text.setText("Ecco il tuo portafoglio");
        //}
        buttonLogin.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butLogin:
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent1, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
            buttonLogin.setVisibility(View.VISIBLE);
            text.setText("Devi fare il login");
        } else {
            buttonLogin.setVisibility(View.GONE);
            text.setText("Ecco il tuo portafoglio");
        }
    }

}
