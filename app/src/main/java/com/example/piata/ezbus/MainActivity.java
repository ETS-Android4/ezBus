package com.example.piata.ezbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private MapFragment mapFragment;
    private PocketFragment pocketFragment;
    private BuyFragment buyFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    static NavigationView navigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tab1:
                    setFragment(mapFragment);
                    return true;
                case R.id.tab2:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        return false;
                    } else {
                        setFragment(pocketFragment);
                        return true;
                    }
                case R.id.tab3:
                    if (LoginActivity.mAuth.getInstance().getCurrentUser()==null) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(login);
                        return false;
                    } else {
                        setFragment(buyFragment);
                        return true;
                    }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainNav = findViewById(R.id.main_nav);
        mMainFrame = findViewById(R.id.main_frame);
        mapFragment = new MapFragment();
        pocketFragment = new PocketFragment();
        buyFragment = new BuyFragment();

        mDrawerLayout = findViewById(R.id.drag_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerLayout = navigationView.getHeaderView(0);
        TextView navUsername =  headerLayout.findViewById(R.id.textView);
        if (LoginActivity.mAuth.getInstance().getCurrentUser() == null) {
            navUsername.setText("Ospite");
            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        }
        else {
            navUsername.setText(LoginActivity.mAuth.getInstance().getCurrentUser().getEmail());
            MainActivity.navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_profilo).setVisible(true);
            MainActivity.navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
        }

        setFragment(mapFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.main_nav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
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
            //aprire attivita profilo
        }
        if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            setFragment(mapFragment);
            mMainNav.setSelectedItemId(R.id.tab1);

        }
        if (id == R.id.nav_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drag_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
