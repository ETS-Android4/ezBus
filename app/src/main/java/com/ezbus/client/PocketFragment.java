package com.ezbus.client;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class PocketFragment extends Fragment {

    public PocketFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pocket, container, false);
        ViewPager tabView = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(tabView);
        setupViewPager(tabView);
        tabView.setOffscreenPageLimit(2);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private FirebaseUser currentClient = LoginActivity.mAuth.getCurrentUser();
        private DocumentFragment fragTickets = new DocumentFragment();
        private DocumentFragment fragCards = new DocumentFragment();
        private DocumentFragment fragPasses = new DocumentFragment();

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();


        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            setFragments();
            addFragment(fragTickets, "BIGLIETTI");
            addFragment(fragCards, "TESSERE");
            addFragment(fragPasses, "ABBONAMENTI");
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

        private void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }

        private void setFragments() {
            if (currentClient != null) {
                String id = currentClient.getUid();
                FirebaseDatabase.getInstance().getReference("/clients/"+id+"/myPocket")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Pocket pocket = dataSnapshot.getValue(Pocket.class);
                            pocket.checkDocuments();
                            fragTickets.updateDocuments(pocket.getMyTickets());
                            fragCards.updateDocuments(pocket.getMyCards());
                            fragPasses.updateDocuments(pocket.getMyPass());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                });
            }
        }

    }

}