package com.ezbus.authentication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import com.ezbus.R;

public class PrivacyActivity extends AppCompatActivity {


    public String text;
    public TextView privacy;
    public TextView txtPrivacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        this.txtPrivacy = findViewById(R.id.txtPrivacy);
        this.privacy = findViewById(R.id.privacy);

        //genera il viewText dato un file txt (INCOMPLETO)
            text = "";

            try {
                InputStream is = getAssets().open("app/java/res/raw/privacy.txt");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                text = new String(buffer);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            txtPrivacy.setText(text);

        }


    public void onClick(View view) {
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    //Se viene premuto il pulsante Indietro di sistema
    @Override
    public void onBackPressed() {
        finish();
    }

}




