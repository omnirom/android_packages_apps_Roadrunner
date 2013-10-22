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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.omni.roadrunner.R;
import org.omni.roadrunner.WakelocksFragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksven.android.common.privateapiproxies.BatteryStatsProxy;
import com.asksven.android.common.privateapiproxies.BatteryStatsTypes;
import com.asksven.android.common.privateapiproxies.StatElement;
import com.asksven.android.common.privateapiproxies.Wakelock;
import com.fortysevendeg.swipelistview.SwipeListView;

public class WakelocksAdapter extends BaseAdapter {
	public static final String LOG_TAG = WakelocksFragment.class
			.getSimpleName();

	private Context mContext;
	private ArrayList<Wakelock> mAppsWakelocks;

	private static class ViewHolder {
		ImageView ivImage;
		TextView tvTitle;
		TextView tvDescription;
		Button btnAppInfo;
		Button btnKill;
		Button btnBlock;
	}
	
	public WakelocksAdapter(Context ctx) {
		mContext = ctx;
		update(BatteryStatsTypes.STATS_SINCE_CHARGED);
	}

	public boolean update(int refId) {
		BatteryStatsProxy proxy = BatteryStatsProxy.getInstance(mContext);
		ArrayList<StatElement> wakelocks;
		try {
			wakelocks = proxy.getWakelockStats(mContext,
					BatteryStatsTypes.WAKE_TYPE_PARTIAL,
					BatteryStatsTypes.STATS_CURRENT, 0);
		} catch (Exception e) {
			Log.e(LOG_TAG, "Unable to update WakelocksAdapter values", e);
			return false;
		}

		mAppsWakelocks = new ArrayList<Wakelock>();

		for (int i = 0; i < wakelocks.size(); i++) {
			Wakelock wl = (Wakelock) wakelocks.get(i);
			if (((wl.getDuration() / 1000) > 0)) {
				mAppsWakelocks.add(wl);
			}
		}

		Comparator<Wakelock> myCompTime = new Wakelock.WakelockTimeComparator();
		Collections.sort(mAppsWakelocks, myCompTime);

		return true;
	}

	@Override
	public int getCount() {
		if (mAppsWakelocks != null) {
			return mAppsWakelocks.size();
		} else {
			return 0;
		}
	}

	@Override
	public StatElement getItem(int position) {
		if (mAppsWakelocks == null) {
			return null;
		} else {
			return mAppsWakelocks.get(position);
		}
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final StatElement item = getItem(position);

		ViewHolder holder = null;

		if (convertView == null) {
			final LayoutInflater li = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = li.inflate(R.layout.package_row, parent, false);
			holder = new ViewHolder();
			holder.ivImage = (ImageView) convertView
					.findViewById(R.id.iv_image);
			holder.tvTitle = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tvDescription = (TextView) convertView
					.findViewById(R.id.tv_description);
			holder.btnAppInfo = (Button) convertView
					.findViewById(R.id.row_button_1);
			holder.btnKill = (Button) convertView
					.findViewById(R.id.row_button_2);
			holder.btnBlock = (Button) convertView
					.findViewById(R.id.row_button_3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		((SwipeListView) parent).recycle(convertView, position);

		holder.ivImage.setImageDrawable(item.getIcon(mContext));
		holder.tvTitle.setText(item.getFqn(mContext));
		holder.tvDescription.setText(item.getData());

		holder.btnAppInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		holder.btnKill.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		holder.btnBlock.setOnClickListener(new View.OnClickListener() {
			@Override
			@TargetApi(14)
			public void onClick(View v) {

			}
		});

		return convertView;
	}

}
