package com.ezbus.authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.ezbus.R;
import com.ezbus.main.MainActivity;
import com.ezbus.main.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginCompanyActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    SharedPref sharedpref;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpref = new SharedPref(this);

        if(sharedpref.loadNightModeState()==true)
            setTheme(R.style.App_Dark);
        else setTheme(R.style.App_Green);
        setContentView(R.layout.activity_login_company);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editTextEmail = findViewById(R.id.emailCompany);
        editTextPassword = findViewById(R.id.passwordCompany);
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCompany();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void loginCompany() {
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                            View headerLayout = MainActivity.navigationView.getHeaderView(0);
                            TextView navUsername =  headerLayout.findViewById(R.id.textView);
                            if (mAuth.getCurrentUser()!=null)
                                navUsername.setText(mAuth.getCurrentUser().getEmail());
                            finish();
                        } else {
                            Toast.makeText(LoginCompanyActivity.this, "Credenziali errate",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}
