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

package org.omni.roadrunner;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.omni.roadrunner.adapters.PowerProfilesAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass
 */
public class PowerProfileFragment extends Fragment {
    public static final String KEY_PROFILE_IDS = "Profile_IDs";

    /**
     * Use this factory method to create a new instance of this fragment using
     * the provided parameters.
     *
     * @return A new instance of fragment PowerProfileFragment.
     */
    public static PowerProfileFragment newInstance() {
        PowerProfileFragment fragment = new PowerProfileFragment();
        return fragment;
    }

    public PowerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.power_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_profile:
                SharedPreferences sp = getActivity().getSharedPreferences(Utils.KEY_POWER_PROFILE_SETTINGS, 0);

                // Find a free profile ID
                int newProfileId = 0;
                Set<String> profiles = new HashSet<String>(sp.getStringSet(KEY_PROFILE_IDS, new HashSet<String>()));
                SharedPreferences.Editor editor = sp.edit();

                while (profiles.contains(Integer.toString(newProfileId))) {
                    newProfileId++;
                }
                profiles.add(Integer.toString(newProfileId));
                editor.putStringSet(KEY_PROFILE_IDS, profiles);
                editor.commit();

                // Open our setup activity with our new profile ID
                Intent intent = new Intent(getActivity(), ProfileSetupActivity.class);
                intent.putExtra(ProfileSetupActivity.EXTRA_PROFILE_ID, newProfileId);
                startActivity(intent);
                return true;
        }

        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_power_profile, container,
                false);

        ListView lv = (ListView) root.findViewById(R.id.lv_power_profiles);
        lv.setAdapter(new PowerProfilesAdapter(getActivity()));

        return root;
    }

}
