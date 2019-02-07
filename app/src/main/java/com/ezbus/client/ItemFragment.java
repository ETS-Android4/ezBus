package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;

import java.util.Date;

public class ItemFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView text;
    Button button;
    String mNewText;


    public ItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        text = view.findViewById(R.id.item_name);
        button = view.findViewById(R.id.button);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                FragmentManager fm = getFragmentManager();
                PocketFragment fragment = (PocketFragment) fm.findFragmentById(R.id.tab2);
                Pocket p = fragment.getMyPocket();
                p.addPass(new Pass(1, "Camerino", 1.0, new Date()));
                fragment.updatePocket(p);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mNewText != "")
            text.setText(mNewText);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void changeText(String newText) {
        mNewText = newText;
    }

}