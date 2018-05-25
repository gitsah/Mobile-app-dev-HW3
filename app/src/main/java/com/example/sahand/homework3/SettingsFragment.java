package com.example.sahand.homework3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;

import java.lang.ref.WeakReference;

public class SettingsFragment extends Fragment {

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

        new GetSettingsTask(this, 0).execute();

        // Inflate the layout for this fragment
        return view;
    }

    public void saveSettings(View v) {
        LocalSettings localSettings = new LocalSettings(0, reminderTime.getText().toString(),
                maxDistance.getText().toString(), gender.getText().toString(), genderInterest.getText().toString(),
                ageMinInterest.getText().toString(), ageMaxInterest.getText().toString(), privacy.isChecked());

        new UpdateSettingsTask(this, localSettings).execute();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalSettings localSettings = new LocalSettings(0, reminderTime.getText().toString(),
                maxDistance.getText().toString(), gender.getText().toString(), genderInterest.getText().toString(),
                ageMinInterest.getText().toString(), ageMaxInterest.getText().toString(), privacy.isChecked());

        new UpdateSettingsTask(this, localSettings).execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        new GetSettingsTask(this, 0).execute();

    }

    private static class GetSettingsTask extends AsyncTask<Void, Void, LocalSettings> {

        private WeakReference<SettingsFragment> weakFragment;
        private int userId;

        GetSettingsTask(SettingsFragment settingsFragment, int userId) {
            weakFragment = new WeakReference<>(settingsFragment);
            this.userId = userId;
        }

        @Override
        protected LocalSettings doInBackground(Void... voids) {
            SettingsFragment settingsFragment = weakFragment.get();
            if (settingsFragment == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(settingsFragment.getContext());

            LocalSettings localSettings = db.settingsDao().findById(userId);

            if (localSettings == null) {
                return null;
            } else
                return localSettings;
        }

        @Override
        protected void onPostExecute(LocalSettings localSettings) {
            SettingsFragment settingsFragment = weakFragment.get();
            if (localSettings == null || settingsFragment == null) {
                return;
            }

            settingsFragment.reminderTime.setText(localSettings.getReminderTime());
            settingsFragment.maxDistance.setText(localSettings.getMaxDistance());
            settingsFragment.gender.setText(localSettings.getGender());
            settingsFragment.genderInterest.setText(localSettings.getGenderInterest());
            settingsFragment.ageMinInterest.setText(localSettings.getAgeMinInterest());
            settingsFragment.ageMaxInterest.setText(localSettings.getAgeMaxInterest());
            settingsFragment.privacy.setChecked(localSettings.isPrivacy());
        }
    }

    private static class UpdateSettingsTask extends AsyncTask<Void, Void, LocalSettings> {

        private WeakReference<SettingsFragment> weakFragment;
        private LocalSettings localSettings;

        public UpdateSettingsTask(SettingsFragment settingsFragment, LocalSettings localSettings) {
            weakFragment = new WeakReference<>(settingsFragment);
            this.localSettings = localSettings;
        }

        @Override
        protected LocalSettings doInBackground(Void... voids) {
            SettingsFragment settingsFragment = weakFragment.get();
            if (settingsFragment == null) {
                return null;
            }

            AppDatabase db = AppDatabaseSingleton.getDatabase(settingsFragment.getContext());

            db.settingsDao().insert(localSettings);
            return localSettings;
        }
    }
}
