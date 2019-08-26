package com.ezbus.authentication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.purchase.Pocket;
import com.ezbus.main.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.ezbus.main.MainActivity.navigationView;

/**
 * Classe che permette l'autenticazione dell'utente all'interno del sistema.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    public static GoogleSignInClient googleSignInClient;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText editTextEmail;
    private EditText editTextPassword;
    private SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editTextEmail = findViewById(R.id.emailCompany);
        editTextPassword = findViewById(R.id.passwordCompany);

        if (sharedpref.isClient()) {
            findViewById(R.id.loginCompany).setVisibility(View.GONE);
            findViewById(R.id.signInUser).setVisibility(View.VISIBLE);
            findViewById(R.id.signInUser).setOnClickListener(this);
        } else {
            findViewById(R.id.signInUser).setVisibility(View.GONE);
            findViewById(R.id.loginCompany).setVisibility(View.VISIBLE);
            findViewById(R.id.signInCompany).setOnClickListener(this);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //Aggiorna il layout dopo che si ritorna nell'activity
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    //Restituisce la risposta dopo la connessione con Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) loginClient(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }
        }
    }

    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    //Inserisce i dati di Google dentro Firebase
    private void loginClient(GoogleSignInAccount acct) {
        final GoogleSignInAccount account = acct;
        Log.d(TAG, "loginClient:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, task -> {
                FirebaseUser user = mAuth.getCurrentUser();
                if (task.isSuccessful()) {
                    if (user!=null) {
                        final String id = user.getUid();
                        FirebaseDatabase.getInstance().getReference("/clients/"+id)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists())
                                    new Client(id, account.getGivenName(), account.getFamilyName(), account.getEmail(), new Pocket());
                                ProfileActivity.setUser(mAuth.getCurrentUser(), sharedpref.getQuery());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                }
                updateUI(user);
            });
    }

    //Autentica l'azienda se i dati sono corretti
    private void loginCompany() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (task.isSuccessful()) {
                        ProfileActivity.setUser(currentUser, sharedpref.getQuery());
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenziali errate", Toast.LENGTH_SHORT).show();
                    }
                    updateUI(currentUser);
                });
        }
    }

    //Genera schermata di Google per la selezione dell'account
    private void externalSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //Esegue il logout dell'utente dall'applicazione
    public static void signOut() {
        mAuth.signOut();
        if (googleSignInClient!=null) googleSignInClient.signOut();
    }

    //Aggiorna menù laterale dopo il login
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //Se l'user è loggato
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            View headerLayout = navigationView.getHeaderView(0);
            TextView navUsername =  headerLayout.findViewById(R.id.textView);
            navUsername.setText(user.getEmail());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("User", user.getUid());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            //Se non è loggato
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signInUser:
                externalSignIn();
                break;
            case R.id.signInCompany:
                loginCompany();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}