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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import org.omni.roadrunner.Constants;
import org.omni.roadrunner.PowerProfileFragment;
import org.omni.roadrunner.ProfileSetupActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PowerProfilesAdapter extends BaseAdapter {
    public static final String LOG_TAG = PowerProfilesAdapter.class
            .getSimpleName();

    private Context mContext;
    private ArrayList<ProfileLineData> mProfilesData;

    private static class ProfileLineData {
        int id;
        String title;
    }

    public PowerProfilesAdapter(Context ctx) {
        mContext = ctx;
        update();
    }

    public boolean update() {
        SharedPreferences sp = mContext.getSharedPreferences(Constants.KEY_POWER_PROFILE_SETTINGS, 0);
        Set<String> profilesId = sp.getStringSet(PowerProfileFragment.KEY_PROFILE_IDS, null);

        mProfilesData = new ArrayList<ProfileLineData>();

        if (Constants.DEBUG) Log.d(LOG_TAG, "There are " + profilesId.size() + " profiles");

        if (profilesId != null) {
            for (String id : profilesId) {
                SharedPreferences profile = mContext.getSharedPreferences("Profile_" + id, 0);
                if (profile == null) {
                    Log.e(LOG_TAG, "The profile " + id + " is stored in the ID set, but there is " +
                            "no profile data linked!");
                    continue;
                }

                ProfileLineData line = new ProfileLineData();
                line.title = profile.getString(ProfileSetupActivity.KEY_PROFILE_NAME, "Error");
                line.id = Integer.parseInt(id);
                mProfilesData.add(line);
            }
        }

        notifyDataSetChanged();
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

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                makeLongPressDialog(item);
                return true;
            }
        });

        return convertView;
    }

    private void makeLongPressDialog(final ProfileLineData line) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(line.title)
                .setItems(new String[]{"Edit", "Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(mContext, ProfileSetupActivity.class);
                            intent.putExtra(ProfileSetupActivity.EXTRA_PROFILE_ID, line.id);
                            mContext.startActivity(intent);
                        } else if (which == 1) {
                            SharedPreferences sp = mContext.getSharedPreferences(Constants.KEY_POWER_PROFILE_SETTINGS, 0);
                            Set<String> profilesId = new HashSet<String>(sp.getStringSet(PowerProfileFragment.KEY_PROFILE_IDS, new HashSet<String>()));

                            profilesId.remove(Integer.toString(line.id));

                            Log.e(LOG_TAG, "After delete, " + profilesId.size());

                            SharedPreferences.Editor edit = sp.edit();
                            edit.putStringSet(PowerProfileFragment.KEY_PROFILE_IDS, profilesId);
                            edit.commit();

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    update();
                                }
                            });
                        }
                    }
                });
        builder.create().show();
    }

}
