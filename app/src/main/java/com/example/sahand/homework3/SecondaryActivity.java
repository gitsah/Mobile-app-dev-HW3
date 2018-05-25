package com.example.sahand.homework3;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class SecondaryActivity extends AppCompatActivity {
    private static final String ARG_PARAM_NAMEANDAGE = "nameAndAge";
    private static final String ARG_PARAM_OCCUPATION = "occupation";
    private static final String ARG_PARAM_DESCRIPTION = "description";

    private Bundle profileBundle;
    private MatchesFragment matchesFragment;
    private SettingsFragment settingsFragment;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        adapter = new Adapter(getSupportFragmentManager());

        FirebaseMatchesViewModel firebaseMatchesViewModel = new FirebaseMatchesViewModel();

        profileBundle = new Bundle();
        profileBundle.putString(ARG_PARAM_NAMEANDAGE, getIntent().getStringExtra("NAMEANDAGE"));
        profileBundle.putString(ARG_PARAM_OCCUPATION, getIntent().getStringExtra("OCCUPATION"));
        profileBundle.putString(ARG_PARAM_DESCRIPTION, getIntent().getStringExtra("DESCRIPTION"));

        matchesFragment = new MatchesFragment();

        settingsFragment = new SettingsFragment();

        firebaseMatchesViewModel.getMatches(
                (ArrayList<Match> matches) -> {
                    matchesFragment.updateMatches(matches);

//            matchesBundle = new Bundle();
//            matchesBundle.putParcelableArrayList(ARG_DATA_SET, matches);
//            //matchesBundle.putString("test","test");
//
//            MatchesFragment matchesFragment = new MatchesFragment();
//            matchesFragment.setArguments(matchesBundle);
//
//
//                    System.out.println("here now");
//            adapter.replaceFragment(matchesFragment, "Matches", 1);
                }
        );

        // Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

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


    public void saveSettings(View v) {
        settingsFragment.saveSettings(v);
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        adapter.addFragment(new ProfileFragment(), "Profile", profileBundle);
        adapter.addFragment(matchesFragment, "Matches");
        adapter.addFragment(settingsFragment, "Settings");
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
