package org.omni.roadrunner.managers;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

/**
 * Manages synchronization status (turning off and on)
 */
public class SyncManager {
    private static final String TAG = "SyncManager";
    private static final String AUTHORITY = "OMNI-ROADRUNNER";
    private static SyncManager sDefault;

    private Context mContext;

    private SyncManager() {

    }

    public static SyncManager getDefault() {
        if (sDefault == null) {
            sDefault = new SyncManager();
        }
        return sDefault;
    }

    /**
     * This method must be called at least once to initialize a valid context (e.g. from the
     * root activity). This context will be used for all future operations.
     *
     * @param context The context
     */
    public void setContext(Context context) {
        mContext = context;
    }

    /**
     * @return The sync accounts available on the device
     */
    public Account[] getAccounts() {
        AccountManager am = AccountManager.get(mContext);
        if (am != null) {
            return am.getAccounts();
        } else {
            Log.e(TAG, "AccountManager is null!");
            return null;
        }
    }

    /**
     * Disable or enable completely the synchronization features of the device
     *
     * @param enabled The state (true = sync, false = no sync)
     */
    public void setFullSync(boolean enabled) {
        ContentResolver.setMasterSyncAutomatically(enabled);
    }

    /**
     * Disable or enable the synchronization of a specific account registered on the device
     *
     * @param account The account to toggle
     * @param enabled The state (true = sync, false = no sync)
     */
    public void setAccountSync(Account account, boolean enabled) {
        final AccountManager am = AccountManager.get(mContext);
        ContentResolver.setIsSyncable(account, AUTHORITY, enabled ? 1 : 0);
    }
}
