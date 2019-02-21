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
        ImageView imgDocument = findViewById(R.id.documentImage);
        TextView txtDocument1 = findViewById(R.id.documentName);
        TextView txtDocument2= findViewById(R.id.documentPrice);
        TextView txtDocument3 = findViewById(R.id.documentExpiration);
        TextView txtDocument4 = findViewById(R.id.documentNumber);
        txtDocument1.setText(document.getName());
        txtDocument2.setText("Prezzo: "+ Double.toString(document.getPrice())+"Euro");
        txtDocument3.setText("Giorni rimasti: "+ Integer.toString(document.getExpiration()));
        String numero = document instanceof Ticket ?  Integer.toString(((Ticket) document).getNumber())  : null;
        txtDocument4.setText("Quantità: "+ numero);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}