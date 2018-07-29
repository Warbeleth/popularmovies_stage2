package com.weeturretstudio.warbeleth.android.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.Preference;
import android.util.Log;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private CheckBoxPreference mPopularMovies = null;
    private CheckBoxPreference mTopRatedMovies = null;
    private CheckBoxPreference mFavoriteMovies = null;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.movie_type_preference);

        //SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        //PreferenceScreen prefScreen = getPreferenceScreen();
        //int count = prefScreen.getPreferenceCount();

        //If we weren't using checkboxes, we'd iterate over the preferences like we did in lesson 6.10

        mPopularMovies = (CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.key_popularmovies));
        mTopRatedMovies = (CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.key_topratedmovies));
        mFavoriteMovies = (CheckBoxPreference)getPreferenceManager().findPreference(getString(R.string.key_favoritemovies));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //Identify the changed preference
        Preference preference = findPreference(key);

        if(null != preference) {
            //Update summary
            if(preference instanceof CheckBoxPreference) {
                Log.v("Temp", "Key: " + preference.getKey() + " Value: " + preference.toString());

                if(preference.getKey().equals(getString(R.string.key_popularmovies)) && mPopularMovies.isChecked()) {
                    mTopRatedMovies.setChecked(false);
                    mFavoriteMovies.setChecked(false);
                }

                if(preference.getKey().equals(getString(R.string.key_topratedmovies)) && mTopRatedMovies.isChecked()) {
                    mPopularMovies.setChecked(false);
                    mFavoriteMovies.setChecked(false);
                }

                if (preference.getKey().equals(getString(R.string.key_favoritemovies)) && mFavoriteMovies.isChecked()) {
                    mTopRatedMovies.setChecked(false);
                    mPopularMovies.setChecked(false);
                }
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.v("TAG", "Preference: " + preference.toString() + " New Value: " + newValue.toString());

        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}