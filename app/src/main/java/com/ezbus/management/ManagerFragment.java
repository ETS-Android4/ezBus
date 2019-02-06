package com.ezbus.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ezbus.R;

public class ManagerFragment extends Fragment {


    public ManagerFragment() {
        //Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_manager, container, false);

        Button button = (Button) rootView.findViewById(R.id.addRoute);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddRouteActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
