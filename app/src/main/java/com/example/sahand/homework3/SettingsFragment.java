package com.example.sahand.homework3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsFragment extends Fragment {
    private LocalSettings localSettings;

    private EditText reminderTime;
    private EditText maxDistance;
    private EditText gender;
    private EditText genderInterest;
    private EditText ageMinInterest;
    private EditText ageMaxInterest;
    private Switch privacy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        reminderTime = view.findViewById(R.id.settings_reminder_time_field);
        maxDistance = view.findViewById(R.id.settings_maximum_distance_field);
        gender = view.findViewById(R.id.settings_gender_field);
        genderInterest = view.findViewById(R.id.settings_interested_gender_field);
        ageMinInterest = view.findViewById(R.id.settings_age_range_min_field);
        ageMaxInterest = view.findViewById(R.id.settings_age_range_max_field);
        privacy = view.findViewById(R.id.settings_privacy_switch);


        if(localSettings != null) {
            reminderTime.setText(localSettings.getReminderTime());
            maxDistance.setText(localSettings.getMaxDistance());
            gender.setText(localSettings.getGender());
            genderInterest.setText(localSettings.getGenderInterest());
            ageMinInterest.setText(localSettings.getAgeMinInterest());
            ageMaxInterest.setText(localSettings.getAgeMaxInterest());
            privacy.setChecked(localSettings.isPrivacy());
        }


        // Inflate the layout for this fragment
        return view;
    }

    public void saveSettings(View v) {
        //do things
    }

    @Override
    public void onPause() {
        super.onPause();

        //load fields into the database
    }

    @Override
    public void onResume() {
        super.onResume();


        if(localSettings != null) {
            reminderTime.setText(localSettings.getReminderTime());
            maxDistance.setText(localSettings.getMaxDistance());
            gender.setText(localSettings.getGender());
            genderInterest.setText(localSettings.getGenderInterest());
            ageMinInterest.setText(localSettings.getAgeMinInterest());
            ageMaxInterest.setText(localSettings.getAgeMaxInterest());
            privacy.setChecked(localSettings.isPrivacy());
        }
        //populate fields using the database
    }

}
