package com.example.piata.ezbus;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment implements View.OnClickListener {

    Button button;
    View view;

    public BuyFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy, container, false);
        button = view.findViewById(R.id.butLogin);
        button.setOnClickListener(this);
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

}
