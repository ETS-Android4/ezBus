package code;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    //defining view objects
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextAge;
    private EditText editTextCompany;
    private EditText editTextIVA;
    private EditText editTextEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;
    private User newUser;

    //defining firebaseauth object
    //private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing firebase auth object
        //LoginActivity.mAuth = FirebaseAuth.getInstance();

        //initializing views
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextAge = findViewById(R.id.editTextAge);
        editTextCompany = findViewById(R.id.editTextCompany);
        editTextIVA = findViewById(R.id.editTextIVA);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button signUpButton = findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void registerUser() {
        //getting email and password from edit texts
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String company = editTextCompany.getText().toString().trim();
        String iva = editTextIVA.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Il campo Nome deve essere compilato!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(surname)){
            Toast.makeText(this,"Il campo Cognome deve essere compilato!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(age)){
            Toast.makeText(this,"Il campo Età deve essere compilato!",Toast.LENGTH_LONG).show();
            return;
        }
        if(Integer.parseInt(age)<14){
            Toast.makeText(this,"Per registrarti devi avere almeno 14 anni.",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(company)){
            Toast.makeText(this,"Il campo Nome Azienda deve essere compilato!",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(iva)){
            Toast.makeText(this,"Il campo Partita IVA deve essere compilato!",Toast.LENGTH_LONG).show();
            return;
        }
        if(iva.length() != 11){
            Toast.makeText(this,"La partita IVA è composta da 11 cifre! Riprova",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Inserisci l'Email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(username)){
            Toast.makeText(this,"Inserisci un Username",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Inserisci la Password",Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length()<8) {
            Toast.makeText(this, "La password è troppo corta. Deve essere composta da minimo 8 caratteri.", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registrazione in corso, attendere...");
        progressDialog.show();

        //creating a new user
        newUser = new User(name, surname, age, email, username);
        LoginActivity.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()){
                            //display some message here
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
                            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                            ProfileActivity.setUser(newUser);
                            Toast.makeText(RegisterActivity.this,"Registrazione effettuata con successo!",Toast.LENGTH_LONG).show();
                        } else {
                            //display some message here
                            Toast.makeText(RegisterActivity.this,"L'email è già stata usata. Riprova!",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}