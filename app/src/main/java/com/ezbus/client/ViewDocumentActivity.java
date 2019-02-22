package com.ezbus.client;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.main.SharedPref;

public class ViewDocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPref sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_document);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Document document = (Document) getIntent().getExtras().getSerializable("Document");

        ImageView docImage = findViewById(R.id.documentImage);
        docImage.setImageResource(document.getImage());

        TextView docName = findViewById(R.id.documentName);
        TextView docPrice = findViewById(R.id.documentPrice);
        TextView docExpiration = findViewById(R.id.documentExpiration);
        TextView docNumber = findViewById(R.id.documentNumber);
        docName.setText(document.getName());
        docPrice.setText("Prezzo: " + Double.toString(document.getPrice())+" €");
        docExpiration.setText("Giorni rimasti: " + Integer.toString(document.getExpiration()));
        String number = document instanceof Ticket ?  Integer.toString(((Ticket) document).getNumber())  : "1";
        docNumber.setText("Quantità: " + number);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}