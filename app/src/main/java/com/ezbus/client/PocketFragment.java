package com.ezbus.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ezbus.R;
import com.ezbus.authentication.Client;
import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PocketFragment extends Fragment implements View.OnClickListener {

    View view;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;
    private Pocket myPocket;


    public PocketFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pocket, container, false);
        firstViewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);
        setupViewPager(firstViewPager);
        /*Client client = ProfileActivity.getUser();
        if (client.getMyPocket() != null) {
            myPocket = client.getMyPocket();
        }*/

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public Pocket getMyPocket() {
        return myPocket;
    }

    public void setMyPocket(Pocket myPocket) {
        this.myPocket = myPocket;
    }

    public void updatePocket(Pocket pocket) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("clients").child(uid).child("myPocket").setValue(pocket);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (LoginUserActivity.mAuth.getInstance().getCurrentUser()==null) {
            text.setText("Devi fare il login");
        } else {
            text.setText("Ecco il tuo portafoglio");
        }*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        ItemFragment fragTickets = new ItemFragment();
        ItemFragment fragCards = new ItemFragment();
        ItemFragment fragPasses = new ItemFragment();

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            setFragments();
            addFragment(fragTickets, "Biglietti");
            addFragment(fragCards, "Tessere");
            addFragment(fragPasses, "Abbonamenti");
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void setFragments() {
            fragTickets.changeText("I tuoi Biglietti!");
            fragCards.changeText("Le tue Tessere!");
            fragPasses.changeText("I tuoi Abbonamenti!");
        }

    }

}