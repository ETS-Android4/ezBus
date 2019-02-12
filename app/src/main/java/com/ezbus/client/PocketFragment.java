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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PocketFragment extends Fragment implements View.OnClickListener {

    View view;
    private TabLayout tabLayout;
    private ViewPager firstViewPager;
    private Pocket myPocket;
    Client currentClient = (Client) ProfileActivity.getUser();


    public PocketFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pocket, container, false);
        firstViewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(firstViewPager);
        setupViewPager(firstViewPager);

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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        ItemFragment fragTickets = new ItemFragment(currentClient.getMyPocket().getMyCards());
        ItemFragment fragCards = new ItemFragment(currentClient.getMyPocket().getMyCards());
        ItemFragment fragPasses = new ItemFragment(currentClient.getMyPocket().getMyCards());

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            addFragment(fragTickets, "Biglietti");
            addFragment(fragCards, "Tessere");
            addFragment(fragPasses, "Abbonamenti");
            setFragments();
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
            //fragTickets.changeText("I tuoi Biglietti!");
            //fragCards.changeText("Le tue Tessere!");
            //fragPasses.changeText("I tuoi Abbonamenti!");
            //Client currentClient = (Client) ProfileActivity.getUser();
            //fragTickets.updateItem(currentClient.getMyPocket().getMyTickets(), getContext());
            //fragCards.updateItem(currentClient.getMyPocket().getMyCards(), getContext());
            //fragPasses.updateItem(currentClient.getMyPocket().getMyPasses(), getContext());
        }

    }

}