package org.omni.roadrunner;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;


public class ProfileSetupActivity extends PreferenceActivity {

    public static final String EXTRA_PROFILE_ID = "uniqueId";

    // keep in tune with xml
    public static final String KEY_DISABLE_WIFI = "disable_wifi";
    public static final String KEY_DISABLE_BLUETOOTH = "disable_bluetooth";
    public static final String KEY_DISABLE_NFC = "disable_nfc";
    public static final String KEY_PROFILE_NAME = "profile_name";
    public static final String KEY_SYNC_FREQUENCY = "sync_frequency";


    @SuppressWarnings("deprecation")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (getIntent().hasExtra(EXTRA_PROFILE_ID)) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int profileId = extras.getInt(EXTRA_PROFILE_ID);
                getPreferenceManager().setSharedPreferencesName(String.format("Profile_%d", profileId));
            }
        } else {
            Toast.makeText(this, "ERR: No UniqueID!", Toast.LENGTH_SHORT).show();
            finish();
        }

        addPreferencesFromResource(R.xml.pref_power_profile);
        bindPreferenceSummaryToValue(findPreference(KEY_DISABLE_WIFI));
        bindPreferenceSummaryToValue(findPreference(KEY_PROFILE_NAME));
        bindPreferenceSummaryToValue(findPreference(KEY_SYNC_FREQUENCY));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return false;
    }

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference
                        .setSummary(index >= 0 ? listPreference.getEntries()[index]
                                : null);

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference
                .setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(
                preference,
                getPreferenceManager().getSharedPreferences().getString(preference.getKey(),
                        ""));
    }

}
