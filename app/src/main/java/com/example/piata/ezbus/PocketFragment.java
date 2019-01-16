package com.example.piata.ezbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */

public class PocketFragment extends Fragment implements View.OnClickListener {

    TextView text;
    View view;


    public PocketFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pocket, container, false);
        text = view.findViewById(R.id.text_pocket);
        text.setText("Ecco il tuo portafoglio");
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
            text.setText("Devi fare il login");
        } else {
            text.setText("Ecco il tuo portafoglio");
        }
    }

}
