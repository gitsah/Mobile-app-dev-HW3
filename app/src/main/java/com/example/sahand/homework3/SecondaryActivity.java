package com.example.sahand.homework3;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class SecondaryActivity extends AppCompatActivity {
    private static final String ARG_PARAM_NAMEANDAGE = "nameAndAge";
    private static final String ARG_PARAM_OCCUPATION = "occupation";
    private static final String ARG_PARAM_DESCRIPTION = "description";
    private static final String ARG_DATA_SET = "matches";

    private Bundle profileBundle;
    private Bundle matchesBundle;
    private FirebaseMatchesViewModel firebaseMatchesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        firebaseMatchesViewModel = new FirebaseMatchesViewModel();

        profileBundle = new Bundle();
        profileBundle.putString(ARG_PARAM_NAMEANDAGE, getIntent().getStringExtra("NAMEANDAGE"));
        profileBundle.putString(ARG_PARAM_OCCUPATION, getIntent().getStringExtra("OCCUPATION"));
        profileBundle.putString(ARG_PARAM_DESCRIPTION, getIntent().getStringExtra("DESCRIPTION"));

        // Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        firebaseMatchesViewModel.getMatches(
                (ArrayList<Match> matches) -> {
            FragmentManager manager = getSupportFragmentManager();
            MatchesFragment fragment = (MatchesFragment) manager.findFragmentByTag("matchesFragment");

            if (fragment != null) {
                // Remove fragment to re-add it
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
            }

            matchesBundle = new Bundle();
            matchesBundle.putParcelableArrayList(ARG_DATA_SET, matches);

            MatchesFragment matchesFragment = new MatchesFragment();
            matchesFragment.setArguments(matchesBundle);

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.matchesView, matchesFragment, "matchesFragment");
            transaction.commit();
        }
        );

//        if (savedInstanceState != null) {
//            nameAndAge.setText(savedInstanceState.getString("NAMEANDAGE"));
//            occupation.setText(savedInstanceState.getString("OCCUPATION"));
//            description.setText(savedInstanceState.getString("DESCRIPTION"));
//        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putString("NAMEANDAGE", nameAndAge.getText().toString());
//        savedInstanceState.putString("OCCUPATION", occupation.getText().toString());
//        savedInstanceState.putString("DESCRIPTION", description.getText().toString());
//
//        super.onSaveInstanceState(savedInstanceState);
//    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ProfileFragment(), "Profile", profileBundle);
        adapter.addFragment(new MatchesFragment(), "Matches", matchesBundle);
        adapter.addFragment(new SettingsFragment(), "Settings");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void addFragment(Fragment fragment, String title, Bundle bundle) {
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
