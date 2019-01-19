package code;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private MapFragment mapFragment;
    private PocketFragment pocketFragment;
    private BuyFragment buyFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static NavigationView navigationView;
    SharedPref sharedpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        else setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        if (getAnswer().equals("Empty")) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            if (getIntent().getBooleanExtra("EXIT", false)) finish();

            mMainFrame = findViewById(R.id.main_frame);
            mMainNav = findViewById(R.id.main_nav);
            if (getAnswer().equals("User")) {
                mMainNav.getMenu().removeItem(R.id.tab4);
                mMainNav.getMenu().removeItem(R.id.tab5);
            } else if (getAnswer().equals("Company")) {
                mMainNav.getMenu().removeItem(R.id.tab2);
                mMainNav.getMenu().removeItem(R.id.tab3);
            }
            mapFragment = new MapFragment();
            pocketFragment = new PocketFragment();
            buyFragment = new BuyFragment();

            mDrawerLayout = findViewById(R.id.drag_layout);
            mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mToggle);
            mToggle.syncState();

            //Decommentare per barra menu personalizzata
            //getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            //getSupportActionBar().setCustomView(R.layout.action_bar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null)
                actionBar.setDisplayHomeAsUpEnabled(true);

            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            View headerLayout = navigationView.getHeaderView(0);
            TextView navUsername = headerLayout.findViewById(R.id.textView);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            LoginActivity.mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            if (LoginActivity.mAuth.getInstance().getCurrentUser() == null) {
                navUsername.setText("Ospite");
                MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(false);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
            } else {
                navUsername.setText(LoginActivity.mAuth.getInstance().getCurrentUser().getEmail());
                MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
                MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            }

            setFragment(1);
            mMainNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab1:
                    setFragment(1);
                    return true;
                case R.id.tab2:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        overridePendingTransition(R.transition.fadein, R.transition.fadeout);
                        return false;
                    } else {
                        setFragment(2);
                        return true;
                    }
                case R.id.tab3:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        return false;
                    } else {
                        setFragment(3);
                        return true;
                    }
                case R.id.tab4:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        return false;
                    } else {
                        setFragment(4);
                        return true;
                    }
                case R.id.tab5:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        return false;
                    } else {
                        setFragment(5);
                        return true;
                    }
            }
            return false;
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void setFragment(int fragmentId) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(fragmentId) {
            case 1:
                if(fragmentManager.findFragmentByTag("one") != null) {
                    //Se il fragment esiste, viene mostrato
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("one")).commit();
                } else {
                    //Se il fragment non esiste, lo crea
                    fragmentManager.beginTransaction().add(R.id.main_frame, new MapFragment(), "one").commit();
                }
                //Se altri fragment sono visibili, vengono nascosti
                if(fragmentManager.findFragmentByTag("two") != null){
                    getSupportActionBar().setElevation(10);
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("two")).commit();
                }
                if(fragmentManager.findFragmentByTag("three") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("three")).commit();
                }
                if(fragmentManager.findFragmentByTag("four") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("four")).commit();
                }
                if(fragmentManager.findFragmentByTag("five") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("five")).commit();
                }
                break;
            case 2:
                if(fragmentManager.findFragmentByTag("two") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("two")).commit();
                } else {
                    fragmentManager.beginTransaction().add(R.id.main_frame, new PocketFragment(), "two").commit();
                }
                getSupportActionBar().setElevation(0);
                if(fragmentManager.findFragmentByTag("one") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
                }
                if(fragmentManager.findFragmentByTag("three") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("three")).commit();
                }
                break;
            case 3:
                if(fragmentManager.findFragmentByTag("three") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("three")).commit();
                } else {
                    fragmentManager.beginTransaction().add(R.id.main_frame, new BuyFragment(), "three").commit();
                }
                if(fragmentManager.findFragmentByTag("one") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
                }
                if(fragmentManager.findFragmentByTag("two") != null){
                    getSupportActionBar().setElevation(10);
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("two")).commit();
                }
                break;
            case 4:
                if(fragmentManager.findFragmentByTag("four") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("four")).commit();
                } else {
                    fragmentManager.beginTransaction().add(R.id.main_frame, new ManagerFragment(), "four").commit();
                }
                if(fragmentManager.findFragmentByTag("one") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
                }
                if(fragmentManager.findFragmentByTag("five") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("five")).commit();
                }
                break;
            case 5:
                if(fragmentManager.findFragmentByTag("five") != null) {
                    fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("five")).commit();
                } else {
                    fragmentManager.beginTransaction().add(R.id.main_frame, new DriverFragment(), "five").commit();
                }
                if(fragmentManager.findFragmentByTag("one") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("one")).commit();
                }
                if(fragmentManager.findFragmentByTag("four") != null){
                    fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("four")).commit();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_profilo) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_logout) {
            AlertDialog.Builder logout = new AlertDialog.Builder(MainActivity.this);
            logout.setMessage("Vuoi davvero uscire?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Se utente accetta di uscire
                            LoginActivity.mAuth.getInstance().signOut();
                            LoginActivity.mGoogleSignInClient.signOut();
                            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Se annulla l'operazione
                        }
                    });
            logout.show();

        }
        if (id == R.id.nav_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drag_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getAnswer() {
        SharedPreferences sp = getSharedPreferences("pref",0);
        return sp.getString("Scelta","Empty");
    }
}