package com.ezbus.authentication;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ezbus.main.MainActivity;
import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextCompany;
    private EditText editTextIVA;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
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

        Button signUpButton = findViewById(R.id.buttonSignup);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void registerUser() {
        //Parametri di Input
        String company = editTextCompany.getText().toString().trim();
        String iva = editTextIVA.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
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
                    final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    currentUser.sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                            newCompany.setUid(currentUser.getUid());
                            FirebaseDatabase.getInstance().getReference().child(sharedpref.getQuery()).child(currentUser.getUid()).setValue(newCompany);
                            //ProfileActivity.setUser(newCompany);
                            Toast.makeText(RegisterActivity.this, "Ti è stata inviata una email di conferma. Apri la email per confermare la registrazione", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        }

                    });
                } else {
                    Toast.makeText(RegisterActivity.this,"L'email non è valida o è già stata usata. Riprova!",Toast.LENGTH_LONG).show();
                }
                }
            });
    }

    private boolean checkData(String company, String iva, String username, String email, String password) {
        if(TextUtils.isEmpty(company)){
            Toast.makeText(this,"Il campo Nome Azienda deve essere compilato!",Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(iva)){
            Toast.makeText(this,"Il campo Partita IVA deve essere compilato!",Toast.LENGTH_LONG).show();
            return false;
        } else if(iva.length() != 11){
            Toast.makeText(this,"La partita IVA è composta da 11 cifre! Riprova",Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Inserisci l'Email",Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Inserisci un Username",Toast.LENGTH_LONG).show();
            return false;
        } else if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Inserisci la Password",Toast.LENGTH_LONG).show();
            return false;
        } else if (password.length()<8) {
            Toast.makeText(this, "La password deve essere composta da almeno 8 caratteri", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void addCompany(Company c) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user!=null) {
            String uid = user.getUid();
            c.setUid(uid);
            rootRef.child("companies").child(uid).setValue(c);
        }
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}