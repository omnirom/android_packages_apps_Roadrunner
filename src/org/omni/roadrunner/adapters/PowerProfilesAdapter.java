/*
 *  Copyright (C) 2013 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.omni.roadrunner.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import org.omni.roadrunner.PowerProfileFragment;
import org.omni.roadrunner.ProfileSetupActivity;
import org.omni.roadrunner.Utils;
import org.omni.roadrunner.WakelocksFragment;

import java.util.ArrayList;
import java.util.Set;

public class PowerProfilesAdapter extends BaseAdapter {
    public static final String LOG_TAG = PowerProfilesAdapter.class
            .getSimpleName();

    private Context mContext;
    private ArrayList<ProfileLineData> mProfilesData;

    private static class ProfileLineData {
        String title;
    }

    public PowerProfilesAdapter(Context ctx) {
        mContext = ctx;
        update();
    }

    public boolean update() {
        SharedPreferences sp = mContext.getSharedPreferences(Utils.KEY_POWER_PROFILE_SETTINGS, 0);
        Set<String> profilesId = sp.getStringSet(PowerProfileFragment.KEY_PROFILE_IDS, null);

        mProfilesData = new ArrayList<ProfileLineData>();

        Log.e(LOG_TAG, "There are " + profilesId.size() + " profiles");

        if (profilesId != null) {
            for (String id : profilesId) {
                SharedPreferences profile = mContext.getSharedPreferences("Profile_" + id, 0);
                if (profile == null) {
                    Log.e(LOG_TAG, "The profile " + id + " is stored in the ID set, but there is" +
                            "no profile data linked!");
                    continue;
                }

                ProfileLineData line = new ProfileLineData();
                line.title = profile.getString(ProfileSetupActivity.KEY_PROFILE_NAME, "Error");
                mProfilesData.add(line);
            }
        }

        return true;
    }

    @Override
    public int getCount() {
        if (mProfilesData != null) {
            return mProfilesData.size();
        } else {
            return 0;
        }
    }

    @Override
    public ProfileLineData getItem(int position) {
        if (mProfilesData == null) {
            return null;
        } else {
            return mProfilesData.get(position);
        }
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ProfileLineData item = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_single_choice,
                    parent, false);
        }


        ((CheckedTextView) convertView).setText(item.title);

        return convertView;
    }

}
