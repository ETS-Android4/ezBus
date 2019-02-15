package com.ezbus.authentication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCompany;
    private EditText editTextIVA;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkPrivacy;
    private Company newCompany;
    SharedPref sharedpref;


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
        this.editTextUsername = findViewById(R.id.editTextUsername);
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
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if (!checkData(company, iva, username, email, password)) return;

        //Creazione nuova azienda
        newCompany = new Company(company, iva, username, email);
        LoginActivity.mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                //Se avvenuta con successo
                if (task.isSuccessful()) {
                    FirebaseUser currentUser = LoginActivity.mAuth.getCurrentUser();
                    if (currentUser != null) {
                        newCompany.setUid(currentUser.getUid());
                        FirebaseDatabase.getInstance().getReference().child(sharedpref.getQuery()).child(currentUser.getUid()).setValue(newCompany);
                        LoginActivity.mAuth.getInstance().signOut();
                        LoginActivity.mGoogleSignInClient.signOut();
                    }
                    Toast.makeText(RegisterActivity.this, "Registrazione completata", Toast.LENGTH_LONG).show();
                    finish();
                }
                else Toast.makeText(RegisterActivity.this,"L'email non è valida o è già stata usata. Riprova!",Toast.LENGTH_LONG).show();
                }
            });
    }

    private boolean checkData(String company, String iva, String username, String email, String password) {
        if (TextUtils.isEmpty(company)) errorMessage("Il campo Nome Azienda deve essere compilato!");
        else if (TextUtils.isEmpty(iva)) errorMessage("Il campo Partita IVA deve essere compilato!");
        else if (iva.length() != 11) errorMessage("La partita IVA è composta da 11 cifre! Riprova");
        else if (TextUtils.isEmpty(email)) errorMessage("Inserisci l'Email");
        else if (TextUtils.isEmpty(username)) errorMessage("Inserisci un Username");
        else if (TextUtils.isEmpty(password)) errorMessage("Inserisci la Password");
        else if (password.length()<8) errorMessage("La password deve essere composta da almeno 8 caratteri");
        else if (!checkPrivacy.isChecked()) errorMessage("Devi accettare i termini e le condizioni della Privacy");

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