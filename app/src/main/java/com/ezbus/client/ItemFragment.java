package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezbus.R;

public class ItemFragment extends Fragment implements View.OnClickListener {

    View view;
    TextView text;
    String mNewText;


    public ItemFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);
        text = view.findViewById(R.id.item_name);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
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