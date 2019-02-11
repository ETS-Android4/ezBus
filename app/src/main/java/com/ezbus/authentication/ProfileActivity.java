package com.ezbus.authentication;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.main.SharedPref;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private static TextView email;
    private static TextView username;
    private static User client;
    static SharedPref sharedpref;


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

        if (getClient() == null)
            setUser(LoginActivity.mAuth.getCurrentUser(), sharedpref.getQuery(), ProfileActivity.this);
        else
            setDataToView();
    }

    @SuppressLint("SetTextI18n")
    private static void setDataToView() {
        if (email != null) {
            email.setText("Email: " + getClient().getEmail());
            username.setText("Username: " + getClient().getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static User getClient() {
        return client;
    }

    public static void setClient(User newClient) {
        client = newClient;
    }

    /*public static User getUser() {
        return user;
    }*/

    public static void setUser(FirebaseUser newUser, final String type, Context context) {
        if (newUser != null) {
            Query search = FirebaseDatabase.getInstance().getReference().child(type).child(newUser.getUid());
            Toast.makeText(context, newUser.getUid(), Toast.LENGTH_LONG).show();
            search.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (type.equals("clients"))
                        setClient(dataSnapshot.getValue(Client.class));
                    else if (type.equals("companies"))
                        setClient(dataSnapshot.getValue(Company.class));
                    setDataToView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}