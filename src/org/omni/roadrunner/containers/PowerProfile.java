package org.omni.roadrunner.containers;

import android.content.Context;
import android.content.SharedPreferences;

import org.omni.roadrunner.ProfileSetupActivity;

public class PowerProfile {
    // Keep those constants in tune with XML! (strings_activity_profile_setup.xml)
    public final static int DISABLE_WIFI_NEVER = 0;
    public final static int DISABLE_WIFI_WHEN_NO_KNOWN_NET_NEARBY = 1;
    public final static int DISABLE_WIFI_ON_SCREEN_OFF = 2;
    public final static int DISABLE_WIFI_ALWAYS = 3;

    public final static int SYNC_FREQ_DISABLED = 0;
    public final static int SYNC_FREQ_ON_SCREEN_ON = 1;
    public final static int SYNC_FREQ_EVERY_6_HOURS = 2;
    public final static int SYNC_FREQ_NORMAL = 3;

    /**
     * The name of the profile
     */
    public String name;

    /**
     * When true, the bluetooth is disabled in this profile
     */
    public boolean disableBluetooth;

    /**
     * When true, NFC is disabled in this profile
     */
    public boolean disableNfc;

    /**
     * Behavior regarding WiFi, one of DISABLE_WIFI_... constants in this class
     */
    public int disableWifi;

    /**
     * Behavior regarding synchronization frequency, one of SYNC_FREQ_... constants in this class
     */
    public int syncFrequency;

    public static PowerProfile get(Context context, int id) {
        PowerProfile profile = new PowerProfile();
        SharedPreferences sp = context.getSharedPreferences(String.format("Profile_%d", id), 0);

        profile.name = sp.getString(ProfileSetupActivity.KEY_PROFILE_NAME, "");
        profile.disableBluetooth = sp.getBoolean(ProfileSetupActivity.KEY_DISABLE_BLUETOOTH, false);
        profile.disableNfc = sp.getBoolean(ProfileSetupActivity.KEY_DISABLE_NFC, false);
        profile.disableWifi = sp.getInt(ProfileSetupActivity.KEY_DISABLE_WIFI, 0);
        profile.syncFrequency = sp.getInt(ProfileSetupActivity.KEY_SYNC_FREQUENCY, 3);

        return profile;
    }
}
