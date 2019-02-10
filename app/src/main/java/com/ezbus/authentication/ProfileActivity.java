package com.ezbus.authentication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.client.Pocket;
import com.ezbus.main.SharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView email;
    private TextView username;
    private static User user;
    private static Client client;
    SharedPref sharedpref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        email = findViewById(R.id.Email);
        username = findViewById(R.id.Username);

        FirebaseUser currentUser = LoginActivity.mAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Query search = FirebaseDatabase.getInstance().getReference().child(sharedpref.getQuery()).child(currentUser.getUid());
            Toast.makeText(ProfileActivity.this, currentUser.getUid(), Toast.LENGTH_LONG).show();
            search.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //setClient(dataSnapshot.getValue(Client.class));
                    //setDataToView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView() {
        email.setText("Email: " + getClient().getEmail());
        //username.setText("Username: " + user.getName());
        //username.setText("User: " + user.getDisplayName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client newUser) {
        this.client = newUser;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }

}