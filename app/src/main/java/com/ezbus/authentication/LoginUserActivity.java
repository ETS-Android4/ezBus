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
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.client.Pocket;
import com.ezbus.main.MainActivity;
import com.ezbus.main.SharedPref;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginUserActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    public static GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Client newClient;
    SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true)
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        setContentView(R.layout.activity_login_user);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        mStatusTextView = findViewById(R.id.status);
        mDetailTextView = findViewById(R.id.detail);

        findViewById(R.id.signInButton).setOnClickListener(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                final GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        final GoogleSignInAccount account = acct;
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String uid = user.getUid();
                            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("clients").child(uid);
                            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        //Non c'è bisogno di creare una nuova classe Client
                                    } else {
                                        newClient = new Client(account.getGivenName(), account.getFamilyName(), account.getEmail(), new Pocket());
                                        newClient.setUid(uid);
                                        rootRef.setValue(newClient);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            updateUI(user);
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /* Utile perchè forse rimuove proprio dal database l'utente
    private void revokeAccess() {
        mAuth.signOut();
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }*/

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //Se l'user è loggato
            findViewById(R.id.signInButton).setVisibility(View.GONE);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            mStatusTextView.setText(user.getEmail());
            mDetailTextView.setText(user.getUid());
            View headerLayout = MainActivity.navigationView.getHeaderView(0);
            TextView navUsername =  headerLayout.findViewById(R.id.textView);
            navUsername.setText(user.getEmail());
            Intent resultIntent = new Intent();
            resultIntent.putExtra("User", user.getUid());
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        } else {
            //Se non è loggato
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
            mStatusTextView.setText("Non sei loggato");
            mDetailTextView.setText(null);
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signInButton)
            signIn();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}