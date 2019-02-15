package com.ezbus.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezbus.R;

public class ManagerFragment extends Fragment {



    public ManagerFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_manager, container, false);

        Button button1 = rootView.findViewById(R.id.routeManager);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RouteManagerActivity.class);
            startActivity(intent);
        });

        Button button2  = rootView.findViewById(R.id.passManager);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PassManagerActivity.class);
            startActivity(intent);
        });

        return rootView;
    }

}
