package com.ezbus.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ezbus.R;
import com.ezbus.authentication.LoginActivity;
import com.ezbus.authentication.ProfileActivity;
import com.ezbus.authentication.RegisterActivity;
import com.ezbus.purchase.BuyFragment;
import com.ezbus.purchase.PocketFragment;
import com.ezbus.management.ManagerFragment;
import com.ezbus.tracking.DriverFragment;
import com.ezbus.tracking.MapFragment;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity principale dell'applicazione.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static NavigationView navigationView;
    private SharedPref sharedpref;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(this);
        if (sharedpref.loadNightModeState())
            setTheme(R.style.NoApp_Dark);
        else setTheme(R.style.NoApp_Blue);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se non è ancora stata fatta la scelta, si apre la welcome
        if (getAnswer().equals("Empty")) {
            startNewActivity(WelcomeActivity.class);
            finish();
        }
        else {
            if (getIntent().getBooleanExtra("EXIT", false)) finish();
            //Passa al profilo il parametro clients o company in base alla scelta fatta
            ProfileActivity.setUser(LoginActivity.getCurrentUser(), sharedpref.getQuery());

            BottomNavigationView mMainNav = findViewById(R.id.main_nav);
            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            //In base alla scelta fatta, vengono modificati i menù
            if (getAnswer().equals("Client")) {
                mMainNav.getMenu().getItem(2).setTitle("POCKET");
                mMainNav.getMenu().removeItem(R.id.tab4);
                navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
                sharedpref.setClient(true); //Client
            } else if (getAnswer().equals("Company")) {
                mMainNav.getMenu().getItem(2).setTitle("MANAGER");
                mMainNav.getMenu().removeItem(R.id.tab3);
                navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
                sharedpref.setClient(false); //Company
            }

            mMainNav.getMenu().getItem(1).setChecked(true);
            mDrawerLayout = findViewById(R.id.drag_layout);

            View headerLayout = navigationView.getHeaderView(0);
            TextView navUsername = headerLayout.findViewById(R.id.textView);

            //Prende i dati dell'utente se è loggato
            FirebaseUser currentUser = LoginActivity.getCurrentUser();
            if (currentUser == null) {
                navUsername.setText("Ospite");
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                if (!sharedpref.isClient()) navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
            } else {
                navUsername.setText(currentUser.getEmail());
                navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                if (!sharedpref.isClient()) navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            }

            //Imposta come fragment iniziale la mappa
            setFragment(1);
            navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
            mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    }

    //Imposta le azioni corrispondenti alle tab
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                FirebaseUser currentUser = LoginActivity.getCurrentUser();
                int id = item.getItemId();
                if (currentUser == null && (id != R.id.tab0 && id != R.id.tab1)) {
                    startNewActivity(LoginActivity.class);
                    return false;
                } else {
                    switch (id) {
                        case R.id.tab0:
                            //Apre il menù laterale a sinistra
                            if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) mDrawerLayout.openDrawer(Gravity.LEFT);
                            return true;
                        case R.id.tab1:
                            setFragment(1);
                            return true;
                        case R.id.tab2:
                            setFragment(2);
                            return true;
                        case R.id.tab3:
                            setFragment(3);
                            return true;
                        case R.id.tab4:
                            setFragment(4);
                            return true;
                    }
                }
                return false;
            };

    //Viene mostrato il fragment corrispondente alla tab selezionata
    private void setFragment(int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment1 = fragmentManager.findFragmentByTag("1");
        Fragment fragment2 = fragmentManager.findFragmentByTag("2");
        Fragment fragment3 = fragmentManager.findFragmentByTag("3");
        Fragment fragment4 = fragmentManager.findFragmentByTag("4");

        switch(fragmentId) {
            case 1:
                if (fragment1 != null) fragmentManager.beginTransaction().show(fragment1).commit();
                else fragmentManager.beginTransaction().add(R.id.main_frame, new MapFragment(), Integer.toString(fragmentId)).commit();
                if (fragment2 != null) fragmentManager.beginTransaction().hide(fragment2).commit();
                if (fragment3 != null) fragmentManager.beginTransaction().hide(fragment3).commit();
                if (fragment4 != null) fragmentManager.beginTransaction().hide(fragment4).commit();
                break;
            case 2:
                if (fragment2 != null) fragmentManager.beginTransaction().show(fragment2).commit();
                else {
                    if (sharedpref.isClient()) fragmentManager.beginTransaction().add(R.id.main_frame, new PocketFragment(), Integer.toString(fragmentId)).commit();
                    else fragmentManager.beginTransaction().add(R.id.main_frame, new ManagerFragment(), Integer.toString(fragmentId)).commit();
                }
                if (fragment1 != null) fragmentManager.beginTransaction().hide(fragment1).commit();
                if (fragment3 != null) fragmentManager.beginTransaction().hide(fragment3).commit();
                if (fragment4 != null) fragmentManager.beginTransaction().hide(fragment4).commit();
                break;
            case 3:
                if (fragment3 != null) fragmentManager.beginTransaction().show(fragment3).commit();
                else fragmentManager.beginTransaction().add(R.id.main_frame, new BuyFragment(), Integer.toString(fragmentId)).commit();
                if (fragment1 != null) fragmentManager.beginTransaction().hide(fragment1).commit();
                if (fragment2 != null) fragmentManager.beginTransaction().hide(fragment2).commit();
                break;
            case 4:
                if (fragment4 != null) fragmentManager.beginTransaction().show(fragment4).commit();
                else fragmentManager.beginTransaction().add(R.id.main_frame, new DriverFragment(), Integer.toString(fragmentId)).commit();
                if (fragment1 != null) fragmentManager.beginTransaction().hide(fragment1).commit();
                if (fragment2 != null) fragmentManager.beginTransaction().hide(fragment2).commit();
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //Azioni dei bottoni presenti nel menù laterale
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profilo:
                startNewActivity(ProfileActivity.class);
                break;
            case R.id.nav_login:
                startNewActivity(LoginActivity.class);
                break;
            case R.id.nav_logout:
                signOut("Vuoi davvero uscire?", MainActivity.class);
                break;
            case R.id.nav_register:
                startNewActivity(RegisterActivity.class);
                break;
            case R.id.nav_settings:
                startNewActivity(SettingsActivity.class);
                break;
            case R.id.nav_welcome:
                signOut("Vuoi ritornare alla schermata iniziale?", WelcomeActivity.class);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drag_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Esegue il logout dopo aver chiesto la conferma
    private void signOut(String message, final Class act) {
        AlertDialog.Builder logout = new AlertDialog.Builder(MainActivity.this);
        logout.setMessage(message).setPositiveButton("Si", (dialog, id) -> {
            //Se l'utente accetta di uscire
            ProfileActivity.resetUser();
            LoginActivity.signOut();
            startNewActivity(act);
            finish();
        })
        .setNegativeButton("No", (dialog, id) -> {
            //Se l'utente annulla l'operazione
        });
        logout.show();
    }

    private void startNewActivity(Class act) {
        Intent intent = new Intent(this, act);
        startActivity(intent);
    }

    //Prende il valore della scelta iniziale
    private String getAnswer() {
        SharedPreferences sp = getSharedPreferences("pref",0);
        return sp.getString("Scelta","Empty");
    }

}