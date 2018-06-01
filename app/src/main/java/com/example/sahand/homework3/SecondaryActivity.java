package com.example.sahand.homework3;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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
    private ArrayList<Match> matches;

    private LocationManager locationManager;
    private double longitudeNetwork, latitudeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        toggleNetworkUpdates();

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
                    this.matches = matches;
                    matchesFragment.updateMatches(this.matches, latitudeNetwork, longitudeNetwork);
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


    private boolean checkLocation() {
        if(!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.enable_location)
                .setMessage(getString(R.string.location_message))
                .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton(R.string.location_cancel, (paramDialogInterface, paramInt) -> {});
        dialog.show();
    }

    public void toggleNetworkUpdates() {
        if(!checkLocation()) {
            return;
        }
            //locationManager.removeUpdates(locationListenerNetwork);

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
            }
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            //hardcoded location value to seattle if device location not working
            //longitudeNetwork = -122.335167;
            //latitudeNetwork = 47.608013;

            runOnUiThread(()-> {
                matchesFragment.updateMatches(matches, latitudeNetwork, longitudeNetwork);
                System.out.println("shoulda been called");
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };
}
