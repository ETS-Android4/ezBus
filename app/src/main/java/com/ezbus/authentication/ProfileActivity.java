package com.ezbus.authentication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

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
    private static User user;
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

        if (getUser() == null)
            setUser(LoginActivity.mAuth.getCurrentUser(), sharedpref.getQuery(), ProfileActivity.this);
        else
            setDataToView();
    }

    @SuppressLint("SetTextI18n")
    private static void setDataToView() {
        if (email != null) {
            email.setText("Email: " + getUser().getEmail());
            username.setText("Username: " + getUser().getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static User getUser() {
        return user;
    }

    public static void resetUser() {
        user = null;
    }

    public static void setUser(FirebaseUser newUser, final String type, Context context) {
        if (newUser != null) {
            Query search = FirebaseDatabase.getInstance().getReference().child(type).child(newUser.getUid());
            search.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (type.equals("clients"))
                        user = dataSnapshot.getValue(Client.class);
                    else if (type.equals("companies"))
                        user = dataSnapshot.getValue(Company.class);
                    setDataToView();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

}