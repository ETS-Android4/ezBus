package com.ezbus.authentication;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
    private static TextView name;
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

        email = findViewById(R.id.email);
        name = findViewById(R.id.name);

        if (user == null) setUser(LoginActivity.mAuth.getCurrentUser(), sharedpref.getQuery());
        else setDataToView();
    }

    private static void setDataToView() {
        if (email != null) {
            email.setText("Email: " + user.getEmail());
            name.setText("Nome: " + user.getName());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public static Client getClient() {
        return (Client) user;
    }

    public static Company getCompany() {
        return (Company) user;
    }

    public static void resetUser() {
        user = null;
    }

    public static void setUser(FirebaseUser newUser, final String type) {
        if (newUser != null) {
            Query search = FirebaseDatabase.getInstance().getReference().child(type).child(newUser.getUid());
            search.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (type.equals("clients"))
                        user = dataSnapshot.getValue(Client.class);
                    else if (type.equals("companies"))
                        user = dataSnapshot.getValue(Company.class);
                    setDataToView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}