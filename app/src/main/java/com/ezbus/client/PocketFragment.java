package com.ezbus.client;

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
import com.ezbus.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PocketFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TabLayout tabLayout;
    private ViewPager tabView;
    FirebaseUser currentClient = LoginActivity.mAuth.getCurrentUser();


    public PocketFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pocket, container, false);
        tabView = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabView);
        setupViewPager(tabView);
        tabView.setOffscreenPageLimit(2);

        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ItemFragment fragTickets = new ItemFragment();
        private ItemFragment fragCards = new ItemFragment();
        private ItemFragment fragPasses = new ItemFragment();

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            setFragments();
            addFragment(fragTickets, "Biglietti");
            addFragment(fragCards, "Tessere");
            addFragment(fragPasses, "Abbonamenti");
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }

        public void setFragments() {
            if (currentClient != null) {
                String id = currentClient.getUid();
                FirebaseDatabase.getInstance().getReference().child("clients").child(id).child("myPocket")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Pocket pocket = dataSnapshot.getValue(Pocket.class);
                            fragTickets.updateItem(pocket.getMyTickets());
                            fragCards.updateItem(pocket.getMyCards());
                            fragPasses.updateItem(pocket.getMyPasses());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                });
            }
        }

    }

}