package com.ashr.weather.fragments;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.ashr.weather.activities.R;
import com.ashr.weather.models.WeatherLocation;


import static com.ashr.weather.utilities.SharedPreferencesHelper.CreateCityInSharedPrefs;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our xml data.
        addPreferencesFromResource(R.xml.preferences);

        // Find the Set City EditTextPreference
        Preference setCityPref = findPreference("set_city");

        WeatherLocation location = new WeatherLocation(getActivity());


        // Set the summary if the SharedPrefs value isn't null.
        if (!location.getName().isEmpty()) {
            setCityPref.setSummary(location.getName());
        }

        // Set an event listener to check for when the city is changed.
        setCityPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object cityValue) {

                CreateCityInSharedPrefs(getActivity(), cityValue.toString());

                WeatherLocation location = new WeatherLocation(getActivity());

                if (!location.getName().isEmpty()) {
                    // Set the summary to the new value.
                    preference.setSummary(location.getName());
                }

                // Go back to the home screen.
                getActivity().onBackPressed();

                return true;
            }
        });

    }
}