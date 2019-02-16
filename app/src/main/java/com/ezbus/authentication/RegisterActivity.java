package com.ezbus.authentication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCompany;
    private EditText editTextIVA;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private CheckBox checkPrivacy;
    private Company newCompany;
    private SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.editTextCompany = findViewById(R.id.editTextCompany);
        this.editTextIVA = findViewById(R.id.editTextIVA);
        this.editTextEmail = findViewById(R.id.editTextEmail);
        this.editTextPassword = findViewById(R.id.editTextPassword);
        this.checkPrivacy = findViewById(R.id.checkPrivacy);

        Button signUpButton = findViewById(R.id.buttonSignup);
        signUpButton.setOnClickListener(this);
        Button privacyButton = findViewById(R.id.buttonPrivacy);
        privacyButton.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void registerCompany() {
        //Parametri di Input
        String company = editTextCompany.getText().toString().trim();
        String iva = editTextIVA.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if (!checkData(company, iva, email, password)) return;

        //Creazione nuova azienda
        LoginActivity.mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                //Se avvenuta con successo
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        new Company(currentUser.getUid(), company, iva, email);
                        LoginActivity.mAuth.getInstance().signOut();
                    }
                    Toast.makeText(RegisterActivity.this, "Registrazione completata", Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(RegisterActivity.this,"L'email non è valida o è già stata usata. Riprova!",Toast.LENGTH_LONG).show();
            });
    }

    private boolean checkData(String company, String iva, String email, String password) {
        if (TextUtils.isEmpty(company)) return errorMessage("Il campo Nome Azienda deve essere compilato!");
        else if (TextUtils.isEmpty(iva)) return errorMessage("Il campo Partita IVA deve essere compilato!");
        else if (iva.length() != 11) return errorMessage("La partita IVA è composta da 11 cifre! Riprova");
        else if (TextUtils.isEmpty(email)) return errorMessage("Inserisci l'Email");
        else if (TextUtils.isEmpty(password)) return errorMessage("Inserisci la Password");
        else if (password.length()<8) return errorMessage("La password deve essere composta da almeno 8 caratteri");
        else if (!checkPrivacy.isChecked()) return errorMessage("Devi accettare i termini e le condizioni della Privacy");

        return true;
    }

    private boolean errorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        return false;
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(this, act);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSignup:
                registerCompany();
                break;
            case R.id.buttonPrivacy:
                startNewActivity(PrivacyActivity.class);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}