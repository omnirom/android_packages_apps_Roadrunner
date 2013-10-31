package org.omni.roadrunner.managers;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.nfc.NfcManager;
import android.util.Log;

import org.omni.roadrunner.containers.PowerProfile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Manages power profiles
 */
public class PowerProfileManager {
    private static final String TAG = "PowerProfileManager";
    private static PowerProfileManager sDefault;
    private PowerProfile mCurrentProfile;

    private PowerProfileManager() {

    }

    public static PowerProfileManager getDefault() {
        if (sDefault == null) {
            sDefault = new PowerProfileManager();
        }
        return sDefault;
    }

    /**
     * @return The current enabled profile, or null if no profile is selected
     */
    public PowerProfile getCurrentProfile() {
        return mCurrentProfile;
    }

    /**
     * Applies the profile having the specified ID
     * @param id The ID of the profile to run
     */
    public void applyProfile(Context context, int id) {
        // Apply each profile directive sequentially
        PowerProfile profile = PowerProfile.get(context, id);

        // Set Wi-Fi state
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // We only deal with Wi-Fi status togglation if it's turned on, otherwise, let user
        // do what he wants.
        if (wifiManager.isWifiEnabled()) {
            switch (profile.disableWifi) {
                case PowerProfile.DISABLE_WIFI_ALWAYS:
                    wifiManager.setWifiEnabled(false);
                    break;

                case PowerProfile.DISABLE_WIFI_WHEN_NO_KNOWN_NET_NEARBY:
                    if (!hasKnownNetwork(wifiManager)) {
                        wifiManager.setWifiEnabled(false);
                    }
                    break;

                default:
                    // Other ways are handled by other handlers on events
                    break;
            }
        }

        // Set Bluetooth state
        BluetoothManager btManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (profile.disableBluetooth) {
            btManager.getAdapter().disable();
        }

        // Set NFC state
        NfcManager nfcManager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        if (profile.disableNfc) {
            // GOOGLE! Y U NO PUT PUBLIC API TO DISABLE OR ENABLE NFC!11!!!!11!!1!
            try {
                Class NfcManagerClass = Class.forName(nfcManager.getDefaultAdapter().getClass().getName());
                Method setNfcDisabled  = NfcManagerClass.getDeclaredMethod("disable");
                setNfcDisabled.setAccessible(true);
                Boolean Nfc             = (Boolean) setNfcDisabled.invoke(nfcManager.getDefaultAdapter());
                boolean success         = Nfc;

                if (!success) {
                    Log.e(TAG, "Unable to turn off NFC (unsuccessful invoke)");
                }
            } catch (Exception e) {
                Log.e(TAG, "Unable to turn off NFC", e);
            }
        }

        // Set sync state
        if (profile.syncFrequency == PowerProfile.SYNC_FREQ_DISABLED) {
            SyncManager.getDefault().setFullSync(false);
        }
    }

    public static boolean hasKnownNetwork(WifiManager wifiMan) {
        List<WifiConfiguration> known = wifiMan.getConfiguredNetworks();
        List<ScanResult> scanned = wifiMan.getScanResults();

        for (ScanResult scan : scanned) {
            for (WifiConfiguration know : known) {
                if (scan.SSID.equals(know.SSID)) {
                    return true;
                }
            }
        }

        return false;
    }
}
