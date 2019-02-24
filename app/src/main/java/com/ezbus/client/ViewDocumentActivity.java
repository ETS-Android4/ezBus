package com.ezbus.client;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.main.SharedPref;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Classe che permette di visualizzare i dettagli di un titolo di viaggio.
 * Utilizzata per mostrare sia documenti già acquistati che pronti per essere acquistati.
 */

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

        //Prende l'oggetto passato dall'activity precedente
        Document document = (Document) getIntent().getExtras().getSerializable("Document");
        boolean buy = getIntent().getExtras().getBoolean("Buy");
        setDataToView(document);

        //Se il documento proviene da un'activity per l'acquisto
        if (buy) {
            Button buttonBuy = findViewById(R.id.buttonBuy);
            buttonBuy.setVisibility(View.VISIBLE);
            buttonBuy.setOnClickListener(view -> {
                Pocket myPocket = ProfileActivity.getClient().getMyPocket();
                if (myPocket.getCredit()>=document.getPrice()) {
                    if (myPocket.add(document)) Toast.makeText(getApplicationContext(), "Già possiedi questo titolo", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "Titolo di viaggio acquistato", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Credito insufficiente", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setDataToView(Document document) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        ImageView docImage = findViewById(R.id.documentImage);
        docImage.setImageResource(document.giveImage());
        TextView docName = findViewById(R.id.documentName);
        TextView docPrice = findViewById(R.id.documentPrice);
        TextView docExpiration = findViewById(R.id.documentExpiration);
        TextView docNumber = findViewById(R.id.documentNumber);
        docName.setText(document.getName());
        docPrice.setText("Prezzo: " + formatter.format(document.getPrice()) + " €");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        docExpiration.setText("Scadenza: " + dateFormat.format(document.getExpiration()));
        String number = document instanceof Ticket ? Integer.toString(((Ticket) document).getNumber()) : "1";
        docNumber.setText("Quantità: " + number);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}